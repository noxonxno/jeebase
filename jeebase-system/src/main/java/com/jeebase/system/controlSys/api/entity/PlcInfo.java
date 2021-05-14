package com.jeebase.system.controlSys.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("plc_info")
public class PlcInfo {
    private String plcId;
    private String deviceId;
    private String deviceCode;
    private String plcName;
    private String plcNote;
    private String plcValue;
}
