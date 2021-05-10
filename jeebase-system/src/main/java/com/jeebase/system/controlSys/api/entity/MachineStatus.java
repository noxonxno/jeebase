package com.jeebase.system.controlSys.api.entity;

import lombok.Data;

import java.util.List;

@Data
public class MachineStatus {
    //机器编码
    private String machine_code;
    //1激光，2等离子
    private String machine_type;
    //0空闲，1忙碌，2故障
    private String status;
    //工位编码
    private String station_code;

    private List<MachineStatus> request_data;
}
