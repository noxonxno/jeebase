package com.jeebase.system.controlSys.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.jeebase.system.controlSys.api.entity.PlcInfo;
import com.jeebase.system.controlSys.api.mapper.PlcInfoMapper;
import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.lib.common.AlreadyConnectedException;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.*;
import org.openscada.opc.lib.da.browser.FlatBrowser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.UnknownHostException;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;

@Service
public class OpcClientService {
    private Server mServer = null;
//    private ServerList mServerList = null;
    private AccessBase access = null;
    @Autowired
    private PlcInfoMapper plcInfoMapper;

    @Value("${opcServer.host}")
    private String host;

    @Value("${opcServer.period}")
    private int period;
    @Value("${opcServer.domain}")
    private String domain;
    @Value("${opcServer.user}")
    private String user;
    @Value("${opcServer.passWord}")
    private String passWord;
    @Value("${opcServer.clsid}")
    private String clsid;

    /**
     * 连接opc server
     */
    public OpcClientService() {
        try {
            final ConnectionInformation ci = new ConnectionInformation();
            ci.setHost(host); // 本机IP
            ci.setDomain(domain); // 域，为空就行
            ci.setUser(user); // 本机上自己建好的用户名
            ci.setPassword(passWord); // 密码
            ci.setClsid(clsid); // KEPServer的注册表ID，可以在“组件服务”里看到，上面有图片说明
            mServer = new Server(ci, Executors.newSingleThreadScheduledExecutor());
//            mServerList = new ServerList("192.168.0.101", "OPCuser", "Dyrj888", "");
            mServer.connect();
            updatePoints();
        } catch (IllegalArgumentException | UnknownHostException | JIException | AlreadyConnectedException e) {
            e.printStackTrace();
        }
    }

    /**
     * 断开连接opc server
     */
    public void disconnectServer() {
        mServer.disconnect();
    }

    public void stopRead() {
        try {
            access.unbind();
        } catch (JIException e) {
            e.printStackTrace();
        }
    }

    /*
     * 显示server上的OPC服务器应用列表
     */
//    public void showAllOPCServer() {
//        try {
//
//// 支持DA 1.0，DA 2.0规范
//            Collection<ClassDetails> detailsList = mServerList.listServersWithDetails(
//                    new Category[]{Categories.OPCDAServer10, Categories.OPCDAServer20}, new Category[]{});
//            FlatBrowser flatBrowser = mServer.getFlatBrowser();
//            Collection<String> browse = flatBrowser.browse();
//            for (String s : browse) {
//                System.out.println(s);
//            }
//            for (final ClassDetails details : detailsList) {
//                System.out.println("ClsId=" + details.getClsId() + " ProgId=" + details.getProgId() + " Description="
//                        + details.getDescription());
//                System.out.println(details);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 检查opc server中是否包含指定的监测点列表
     */
    public boolean checkItemList(List<String> list) {
// 获取opc server上的所有检测点
        FlatBrowser flatBrowser = mServer.getFlatBrowser();
        if (flatBrowser == null) {
            return false;
        }

        try {
            Collection<String> collection = flatBrowser.browse();

            return collection.containsAll(list);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 异步读取数据 实现了IOPCDataCallback接口，基于事件回调的实现
     */
    public String asyncReadObject(final String itemId) throws Exception {
        final String[] s = new String[1];
// 第三个参数用于设置初始化时是否执行访问
        try {

            access = new SyncAccess(mServer, period);
            // 这是个回调函数，就是读到值后执行这个打印，是用匿名类写的，当然也可以写到外面去
            access.addItem(itemId, new DataCallback() {
                @Override
                public void changed(Item item, ItemState itemState) {
                    int type = 0;
                    try {
                        type = itemState.getValue().getType();
                    } catch (JIException e) {
                        e.printStackTrace();
                    }
                    // 如果读到是short类型的值
                    if (type == JIVariant.VT_I2) {
                        short n = 0;
                        try {
                            n = itemState.getValue().getObjectAsShort();
                        } catch (JIException e) {
                            e.printStackTrace();
                        }
                        s[0] = String.valueOf(n);

                    }
                    // 如果读到是字符串类型的值
                    if (type == JIVariant.VT_BSTR) { // 字符串的类型是8
                        String str = "";
                        try {
                            JIString value = itemState.getValue().getObjectAsString();
                            str = value.getString(); // 得到字符串
                        } catch (JIException e) {
                            e.printStackTrace();
                        } // 按字符串读取
                        s[0] = String.valueOf(str);
                    }
                    // 如果读到是字符串类型的值
                    if (type == JIVariant.VT_UI2) { // 字符串的类型是18

                        Object value = null;
                        try {
                            value = itemState.getValue().getObjectAsUnsigned().getValue();
                        } catch (JIException e) {
                            e.printStackTrace();
                        }
//                            String str = value.getString(); // 得到字符串
                        s[0] = String.valueOf(value);
                    }
                }
            });
            // start reading，开始读值
            access.bind();
            return s[0];
        } catch (final JIException e) {
            System.out.printf("%08X: %s%n", e.getErrorCode(), mServer.getErrorMessage(e.getErrorCode()));

        }
        return "err";
    }

    public void updatePoints() {
        //读取数据库Item列表
        QueryWrapper<PlcInfo> wrapper = new QueryWrapper<>();
        wrapper.select("plc_name", "plc_value");
        List<PlcInfo> item = plcInfoMapper.selectList(wrapper);
        //循列表读值
        for (PlcInfo plcInfo : item) {
            String plc_name1 = plcInfo.getPlcName();
            try {
                String s = asyncReadObject(plc_name1);
                if (s != null && !s.equals(plcInfo.getPlcValue())) {
                    UpdateWrapper<PlcInfo> updateWrapper = new UpdateWrapper<>();
                    updateWrapper.eq("name", "rhb");
                    PlcInfo plc = new PlcInfo();
                    plc.setPlcValue(s);
                    plcInfoMapper.update(plc, updateWrapper);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void writeObject(final String itemId, String writeValue) throws Exception {

        try {
            // Add a new group，添加一个组，这个用来就读值或者写值一次，而不是循环读取或者写入
            // 组的名字随意，给组起名字是因为，server可以addGroup也可以removeGroup，读一次值，就先添加组，然后移除组，再读一次就再添加然后删除
            final Group group = mServer.addGroup("test");
            // 将一个item加入到组，item名字就是MatrikonOPC Server或者KEPServer上面建的项的名字比如：u.u.TAG1，PLC.S7-300.TAG1
            final Item item = group.addItem(itemId);
            final JIVariant value = new JIVariant(writeValue);
            try {
                item.write(value);
            } catch (JIException e) {
                e.printStackTrace();
            }
        } catch (final JIException e) {
            System.out.println(String.format("%08X: %s", e.getErrorCode(), mServer.getErrorMessage(e.getErrorCode())));
        }
    }
}

