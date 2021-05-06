package com.jeebase.system.controlSys.log.entitly;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author zhengkai.blog.csdn.net
 * @description device_log
 * @date 2021-05-06
 */
@Data
@TableName("device_log")
public class DeviceLog implements Serializable {

    private static final long serialVersionUID = 1L;


    /**
     * log_id
     */
    @TableId("log_id")
    private String logId;

    /**
     * 设备id
     */
    @TableField("device_id")
    private String deviceId;

    /**
     * 设备编码
     */
    @TableField("device_code")
    private String deviceCode;

    /**
     * 设备名称
     */
    @TableField("device_name")
    private String deviceName;

    /**
     * 设备类型
     */
    @TableField("device_type")
    private String deviceType;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 主计划id
     */
    @TableField("plan_id")
    private String planId;

    /**
     * 主计划编码
     */
    @TableField("plan_code")
    private String planCode;

    /**
     * 设备动作详情
     */
    @TableField("action_details")
    private String actionDetails;
}
