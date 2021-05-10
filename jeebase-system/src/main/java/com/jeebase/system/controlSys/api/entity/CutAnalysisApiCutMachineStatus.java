package com.jeebase.system.controlSys.api.entity;

import lombok.Data;

import java.util.List;

@Data
public class CutAnalysisApiCutMachineStatus {
    private String request_uuid;
    private String request_time;
    private List<MachineStatus> request_data;
}
