package com.jeebase.system.controlSys.baseInfo.location.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@TableName("location")
public class LocationEntitly implements Serializable {
    /**
     * 库位id
     */
    @TableId("location_id")
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
    @NotBlank(message="库位存放型号不能为空")
    private String steelType;
    /**
     * 库位最大可存放
     */
    @NotBlank(message="库位最大可存放量不能为空")
    private Integer locationMaxNum;
    /**
     * 库存数量
     */
    @NotBlank(message="库存数量不能为空")
    private Integer locationTotal;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.UPDATE, update = "now()")
    private LocalDateTime updateTime;

    @TableField(value = "update_user", fill = FieldFill.UPDATE)
    private String updateUser;

    @TableField(fill = FieldFill.INSERT)
    private String locationUser;
}
