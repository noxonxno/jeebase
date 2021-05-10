package com.jeebase.system.controlSys.api.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SortingApiPlanFJExecutionResults {
    private String request_code;
    private Date request_time;
    private String request_name;
    private PlanFJResults request_data;
}
