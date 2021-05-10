package com.jeebase.system.controlSys.api.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SortingApiPlanPrintExecutionResults {
    private String request_code;
    private Date request_time;
    private String request_name;
    private PrintResults request_data;
}
