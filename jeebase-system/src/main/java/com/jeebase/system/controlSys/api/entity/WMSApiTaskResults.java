package com.jeebase.system.controlSys.api.entity;

import lombok.Data;

@Data
public class WMSApiTaskResults {
    private String request_uuid;
    private String request_time;
    private WmsAtion request_data;

}
