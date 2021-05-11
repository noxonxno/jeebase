package com.jeebase.system.controlSys.api;


import com.jeebase.system.utils.ReadOPCServerUtils;
import com.jeebase.system.utils.WriteOPCServerUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class TableRollerApi {
    @Value("${opcServer.host}")
    private String host;

    @Value("${opcServer.user}")
    private String user;

    @Value("${opcServer.password}")
    private String password;

    //辊道状态查询
    public Map<String, String> readTableRollerStatus(String domain, String clsid, String itemId) {
        HashMap<String, String> map = new HashMap<>();
        try {
            return ReadOPCServerUtils.readOPCServer(host, domain, password, user, clsid, itemId);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("err", "读取出错");
            return map;
        }
    }

    //辊道位置状态查询
    public Map<String, String> readTableRollerPlace(String domain, String clsid, String itemId) {
        HashMap<String, String> map = new HashMap<>();
        try {
            return ReadOPCServerUtils.readOPCServer(host, domain, password, user, clsid, itemId);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("err", "读取出错");
            return map;
        }
    }

    //写入辊道输送目标位置
    public Map<String, String> writeTableRollerToPlace(String domain, String clsid, String itemId, String writeValue) {
        HashMap<String, String> map = new HashMap<>();
        try {
            return WriteOPCServerUtils.writeOPCServer(host, domain, password, user, clsid, itemId, writeValue);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("err", "写入出错");
            return map;
        }
    }

    //读取RFID
    public Map<String, String> readRFID(String domain, String clsid, String itemId) {
        HashMap<String, String> map = new HashMap<>();
        try {
            return ReadOPCServerUtils.readOPCServer(host, domain, password, user, clsid, itemId);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("err", "读取出错");
            return map;
        }
    }

    //写入RFID
    public Map<String, String> writeRFID(String domain, String clsid, String itemId, String writeValue) {
        HashMap<String, String> map = new HashMap<>();
        try {
            return WriteOPCServerUtils.writeOPCServer(host, domain, password, user, clsid, itemId, writeValue);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("err", "写入出错");
            return map;
        }
    }

    //清空RFID
    public Map<String, String> emptyRFID(String domain, String clsid, String itemId, String writeValue) {
        HashMap<String, String> map = new HashMap<>();
        try {
            return WriteOPCServerUtils.writeOPCServer(host, domain, password, user, clsid, itemId, writeValue);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("err", "写入出错");
            return map;
        }
    }

    //顶升设备状态查询lift
    public Map<String, String> readLiftEquipmentStatus(String domain, String clsid, String itemId) {
        HashMap<String, String> map = new HashMap<>();
        try {
            return ReadOPCServerUtils.readOPCServer(host, domain, password, user, clsid, itemId);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("err", "读取出错");
            return map;
        }
    }

    public Map<String, String> writeTrayIsFinish(String domain, String clsid, String itemId, String writeValue) {
        HashMap<String, String> map = new HashMap<>();
        try {
            return WriteOPCServerUtils.writeOPCServer(host, domain, password, user, clsid, itemId, writeValue);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("err", "写入出错");
            return map;
        }
    }

}
