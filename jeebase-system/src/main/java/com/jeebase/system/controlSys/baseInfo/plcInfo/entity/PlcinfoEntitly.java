package com.jeebase.system.controlSys.baseInfo.plcInfo.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author DELL
 */
@Getter
@Setter
@TableName("plc_info")
public class PlcinfoEntitly implements Serializable {
    @TableId("plc_id")
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


}
