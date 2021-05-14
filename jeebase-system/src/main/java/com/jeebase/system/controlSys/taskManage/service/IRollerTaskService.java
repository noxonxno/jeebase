package com.jeebase.system.controlSys.taskManage.service;

public interface IRollerTaskService {

    public boolean getPlcState(String place);
    public boolean goPlcPlace(String from,String to);

}
