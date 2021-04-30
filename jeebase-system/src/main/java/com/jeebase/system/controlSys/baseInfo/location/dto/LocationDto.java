package com.jeebase.system.controlSys.baseInfo.location.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class LocationDto implements Serializable {
    /**
     * 库位id
     */
    private String locationId;
    /**
     * 库位编号
     */
    private String locationCode;
    /**+
     * 库位状态
     */
    private String locationState;
    /**
     * 库位存放型号
     */
    private String steelType;
    /**
     * 库位最大可存放
     */
    private Integer locationMaxNum;
    /**
     * 库存数量
     */
    private Integer locationTotal;
    /**
     * 更新时间
     */
    private String updateTime;

    private String updateUser;
    private String locationUser;
    private Integer PageSize;
    private Integer PageNumber;
}
