package com.jeebase.system.controlSys.taskManage.service.impl;


import com.jeebase.system.controlSys.api.TableRollerApi;
import com.jeebase.system.controlSys.taskManage.service.IRollerTaskService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

public class RollerTaskServiceImpl implements IRollerTaskService {
    @Autowired
    private TableRollerApi tableRollerApi;

    @Override
    public boolean confirmRoller(String domain, String clsid, String itemId) {
        Map<String, String> map = tableRollerApi.readTableRollerStatus(domain, clsid, itemId);
        Map<String, String> map1 = tableRollerApi.readTableRollerPlace(domain, clsid, itemId);
        if (map.get("err") != null && map1.get("err") != null) {
            return map.get("pointsValue").equals("0") && map1.get("pointsValue").equals("1");
        }
        return false;
    }

    @Override
    public boolean implementRoller(String domain, String clsid, String itemId,String writeValue) {
        Map<String, String> map = tableRollerApi.writeTableRollerToPlace(domain, clsid, itemId, writeValue);
        return map.get(writeValue).equals("success");
    }
}
