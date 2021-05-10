package com.jeebase.system.controlSys.api;


import com.jeebase.system.utils.ReadOPCServerUtils;
import com.jeebase.system.utils.WriteOPCServerUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

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
    public void readTableRollerStatus() {
        System.out.println(host);
        String domain = "";
        String clsid = "7BC0CC8E-482C-47CA-ABDC--0FE7F9C6E729";
        String itemid = "demo.tag设备.tag";
        String host = "192.168.0.112";
        String password = "123456";
        String user = "OPCServer";

        try {
            Map<String, String> stringStringMap = ReadOPCServerUtils.readOPCServer(host, domain, password, user, clsid, itemid);
            System.out.print(stringStringMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //辊道位置状态查询
    public void readTableRollerPlace(String domain, String clsid, String itemId) {
        try {
            ReadOPCServerUtils.readOPCServer(host, domain, password, user, clsid, itemId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //写入辊道输送目标位置
    public void writeTableRollerToPlace(String domain, String clsid, String itemId, String writeValue) {

        try {
            WriteOPCServerUtils.writeOPCServer(host, domain, password, user, clsid, itemId,writeValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //读取RFID
    public void readRFID(String domain, String clsid, String itemId) {
        try {
            ReadOPCServerUtils.readOPCServer(host, domain, password, user, clsid, itemId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //写入RFID
    public void writeRFID(String domain, String clsid, String itemId,String writeValue) {

        try {
            WriteOPCServerUtils.writeOPCServer(host, domain, password, user, clsid, itemId,writeValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //清空RFID
    public void emptyRFID(String domain, String clsid, String itemId,String writeValue) {
        try {
            WriteOPCServerUtils.writeOPCServer(host, domain, password, user, clsid, itemId,writeValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //顶升设备状态查询lift
    public void readLiftEquipmentStatus(String domain, String clsid, String itemId) {
        try {
            ReadOPCServerUtils.readOPCServer(host, domain, password, user, clsid, itemId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void writeTrayIsFinish(String domain, String clsid, String itemId,String writeValue) {
        try {
            WriteOPCServerUtils.writeOPCServer(host, domain, password, user, clsid, itemId,writeValue);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public String test(){
       return host;
    }
}
