package com.jeebase.system.controlSys.api.entity;

import lombok.Data;

@Data
public class CutAnalysisApiPlanCutExecutionResults {
    private String request_uuid;
    private String request_time;
    private CutAtion request_data;
}
