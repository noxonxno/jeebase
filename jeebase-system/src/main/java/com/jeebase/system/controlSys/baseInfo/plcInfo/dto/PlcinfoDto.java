package com.jeebase.system.controlSys.baseInfo.plcInfo.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author DELL
 */
@Getter
@Setter
public class PlcinfoDto implements Serializable {
    private String plcId;
    /**
     * 设备id
     */
    private String deviceId;
    /**
     * 设备编码
     */
    private String deviceCode;
    /**
     * 标识名称
     */
    private String plcName;
    /**
     * 描述
     */
    private String plcNote;

    private Integer pageSize;
    private Integer pageNumber;


}
