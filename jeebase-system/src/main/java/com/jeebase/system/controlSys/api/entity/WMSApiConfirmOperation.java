package com.jeebase.system.controlSys.api.entity;

import lombok.Data;

@Data
public class WMSApiConfirmOperation {
    private String request_uuid;
    private String request_time;
    private Task request_data;

}
