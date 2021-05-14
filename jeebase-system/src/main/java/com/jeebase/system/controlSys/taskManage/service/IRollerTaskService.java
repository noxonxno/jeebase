package com.jeebase.system.controlSys.taskManage.service;

public interface IRollerTaskService {

    boolean initFinish();

    public boolean getPlcState(String place);
    public boolean goPlcPlace(String from,String to);

    boolean fromBufferToCut(String bufferPlace);

    boolean fromCutToBuffer(String cutPlace);

    boolean riseUp(String plc);

    boolean riseDown(String plc);

    boolean write(String plc,String value);

    String read(String value);
}
