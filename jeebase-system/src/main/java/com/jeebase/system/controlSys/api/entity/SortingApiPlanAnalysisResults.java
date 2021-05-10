package com.jeebase.system.controlSys.api.entity;

import lombok.Data;

@Data
public class SortingApiPlanAnalysisResults {
    private String request_code;
    private String request_time;
    private FJPlanList request_data;
}
