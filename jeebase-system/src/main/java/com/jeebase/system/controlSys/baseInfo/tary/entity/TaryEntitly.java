package com.jeebase.system.controlSys.baseInfo.tary.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@TableName("tary")
public class TaryEntitly implements Serializable {
    /**
     * tary_id 托盘id
     */
    @TableId("tary_id")
    private String taryId;

    /**
     * 主计划编码
     */
    private String planCode;

    /**
     * 托盘信息
     */
    @NotBlank(message="托盘信息不能为空")
    private String taryInfo;

    /**
     *托盘状态 0表示空闲 1表示损坏 2表示正在使用 3表示正在维修
     */
    private Integer taryState;

    /**
     * 托盘使用次数
     */
    @NotNull(message="托盘使用次数不能为空")
    private Integer taryNum;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    @TableField(fill = FieldFill.UPDATE)
    private String updateUser;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.UPDATE, update = "now()")
    private LocalDateTime updateTime;

    /**
     * 使用阈值
     */
    @NotNull(message="阈值不能为空")
    private Integer taryFixNum;

    /**
     * 托盘累计使用次数
     */
    private Integer taryMaxNum;
}
