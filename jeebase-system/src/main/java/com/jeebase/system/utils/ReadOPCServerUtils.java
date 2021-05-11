package com.jeebase.system.utils;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.JIString;
import org.jinterop.dcom.core.JIVariant;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;

public class ReadOPCServerUtils {
    public static Map<String, String> readOPCServer(String host, String domain, String password, String user, String clsid, String itemid) throws Exception {
        // 连接信息
        HashMap<String, String> map = new HashMap<>();
        final ConnectionInformation ci = new ConnectionInformation();
        ci.setHost(host); // 本机IP
        ci.setDomain(domain); // 域，为空就行
        ci.setUser(user); // 本机上自己建好的用户名
        ci.setPassword(password); // 密码

        // 使用MatrikonOPC Server的配置
        // ci.setClsid("F8582CF2-88FB-11D0-B850-00C0F0104305"); // MatrikonOPC的注册表ID，可以在“组件服务”里看到
        // final String itemId = "u.u"; // MatrikonOPC Server上配置的项的名字按实际

        // 使用KEPServer的配置
        ci.setClsid(clsid); // KEPServer的注册表ID，可以在“组件服务”里看到，上面有图片说明
        // KEPServer上配置的项的名字，没有实际PLC，用的模拟器：simulator
        // final String itemId = "通道 1.设备 1.标记 1";

        // 启动服务
        final Server server = new Server(ci, Executors.newSingleThreadScheduledExecutor());

        try {
            // 连接到服务
            server.connect();
            // add sync access, poll every 500 ms，启动一个同步的access用来读取地址上的值，线程池每500ms读值一次
            // 这个是用来循环读值的，只读一次值不用这样
            final AccessBase access = new SyncAccess(server, 500);
            // 这是个回调函数，就是读到值后执行这个打印，是用匿名类写的，当然也可以写到外面去
            access.addItem(itemid, new DataCallback() {
                @Override
                public void changed(Item item, ItemState itemState) {
                    int type = 0;
                    try {
                        type = itemState.getValue().getType(); // 类型实际是数字，用常量定义的
                    } catch (JIException e) {
                        e.printStackTrace();
                    }
                    System.out.println("监控项的数据类型是：-----" + type);
                    System.out.println("监控项的时间戳是：-----" + itemState.getTimestamp().getTime());
                    System.out.println("监控项的详细信息是：-----" + itemState);
                    map.put("detail", itemState.toString());

                    // 如果读到是short类型的值
                    if (type == JIVariant.VT_I2) {
                        short n = 0;
                        try {
                            n = itemState.getValue().getObjectAsShort();
                        } catch (JIException e) {
                            e.printStackTrace();
                        }
                        System.out.println("-----short类型值： " + n);
                        map.put("pointsValue", String.valueOf(n));
                    }
                    // 如果读到是字符串类型的值
                    if (type == JIVariant.VT_BSTR) { // 字符串的类型是8
                        try {
                            JIString value = itemState.getValue().getObjectAsString();
                            String str = value.getString(); // 得到字符串
                            System.out.println("-----String类型值： " + str);
                            map.put("pointsValue", String.valueOf(value));
                        } catch (JIException e) {
                            e.printStackTrace();
                        } // 按字符串读取

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
                        System.out.println("-----String类型值： " + value);
                        map.put("pointsValue", String.valueOf(value));
                    }
                }
            });
            // start reading，开始读值
            access.bind();
            // wait a little bit，有个10秒延时
            Thread.sleep(10 * 1000);
            // stop reading，停止读取
            access.unbind();
            return map;
        } catch (final JIException e) {
            System.out.printf("%08X: %s%n", e.getErrorCode(), server.getErrorMessage(e.getErrorCode()));
            map.put(String.valueOf(e.getErrorCode()), server.getErrorMessage(e.getErrorCode()));
            return map;
        }
    }
}
