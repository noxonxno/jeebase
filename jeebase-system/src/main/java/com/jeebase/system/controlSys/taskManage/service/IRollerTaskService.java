package com.jeebase.system.controlSys.taskManage.service;

public interface IRollerTaskService {
    /**
     * 确认辊道
     */
    boolean  confirmRoller(String itemid,String clsid, String itemId);
    /**
     * 实施辊道运动
     */
    boolean implementRoller(String domain, String clsid, String itemId,String writeValue);
}
