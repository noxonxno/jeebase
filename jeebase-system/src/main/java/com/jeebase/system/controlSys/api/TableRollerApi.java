package com.jeebase.system.controlSys.api;


import com.jeebase.system.controlSys.api.service.OpcClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping()
public class TableRollerApi {


    @Autowired
    private OpcClientService opcClientService;

       //辊道状态查询
    @RequestMapping("/Test")
    public String readTableRollerStatus(String itemId) {
        try {
//             opcClientService.updatePoints();
           return opcClientService.asyncReadObject(itemId);
        } catch (Exception e) {
            e.printStackTrace();
//            return map;
            return null;
        }
    }

}
