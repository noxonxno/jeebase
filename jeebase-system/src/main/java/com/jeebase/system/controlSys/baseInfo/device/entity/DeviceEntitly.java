package com.jeebase.system.controlSys.baseInfo.device.entity;


import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

/**
 * @author DELL 设备信息表
 */
@Getter
@Setter
@TableName("device")
public class DeviceEntitly {
    @TableId("device_id")
    private String deviceId;
    /**
     * '设备编码'
     */
    @NotBlank(message="设备编码不能为空")
    private String deviceCode;
    /**
     * '设备名称',
     */
    @NotBlank(message="设备名称不能为空")
    private String deviceName;
    /**
     *  设备类型
     * '0：辊道，1：天车，2：喷码机，3切割机，4：举升机，
     *  5：小件分拣机器人，6：大件分拣机器人，
     *  7：沙光机，8：皮带输送线，9：入筐机器人',
     */
    @NotBlank(message="设备类型不能为空")
    private String deviceType;
    /**
     * '0：正常，1：运行中，2：空闲，3：异常',
     */
    private String deviceState;


    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.UPDATE, update = "now()")
    private LocalDateTime updateTime;

    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;
}
