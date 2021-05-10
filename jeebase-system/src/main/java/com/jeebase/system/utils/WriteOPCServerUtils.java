package com.jeebase.system.utils;

import org.jinterop.dcom.common.JIException;
import org.jinterop.dcom.core.*;
import org.openscada.opc.lib.common.ConnectionInformation;
import org.openscada.opc.lib.da.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WriteOPCServerUtils {
    public static Map<String, String> writeOPCServer(String host, String domain, String password, String user, String clsid, String itemid, String writeValue) throws Exception {
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
            // connect to server，连接到服务
            server.connect();
            // Add a new group，添加一个组，这个用来就读值或者写值一次，而不是循环读取或者写入
            // 组的名字随意，给组起名字是因为，server可以addGroup也可以removeGroup，读一次值，就先添加组，然后移除组，再读一次就再添加然后删除
            final Group group = server.addGroup("test");
            // 将一个item加入到组，item名字就是MatrikonOPC Server或者KEPServer上面建的项的名字比如：u.u.TAG1，PLC.S7-300.TAG1
            final Item item = group.addItem(itemid);
            final JIVariant value = new JIVariant(writeValue);
            try {
                item.write(value);
                map.put("成功写入数据：",writeValue);
            } catch (JIException e) {
                e.printStackTrace();
                map.put("写入数据出错：",writeValue);
            }
            return map;
        } catch (final JIException e) {
            System.out.println(String.format("%08X: %s", e.getErrorCode(), server.getErrorMessage(e.getErrorCode())));
            map.put(String.valueOf(e.getErrorCode()), server.getErrorMessage(e.getErrorCode()));
            return map;
        }
    }
}
