package com.jeebase.system.controlSys.api.entity;

import lombok.Data;

@Data
public class CutAnalysisApiPlanCutAnalysisResults {
    private String request_uuid;
    private String request_time;
    private CutTask request_data;
}
