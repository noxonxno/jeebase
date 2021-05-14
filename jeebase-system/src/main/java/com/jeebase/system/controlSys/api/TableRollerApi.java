package com.jeebase.system.controlSys.api;


import com.jeebase.system.controlSys.api.service.OpcClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

@RestController
@RequestMapping()
public class TableRollerApi {


    @Autowired
    private OpcClientService opcClientService;

       //辊道状态查询
    @RequestMapping("/Test")
    public void readTableRollerStatus() {
        HashMap<String, String> map = new HashMap<>();
        final String itemId = "通道 1.设备 1.标记 2";
        try {
             opcClientService.UpdatePoints();
             opcClientService.asyncReadObject(itemId);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("err", "读取出错");
//            return map;
        }
    }

}
