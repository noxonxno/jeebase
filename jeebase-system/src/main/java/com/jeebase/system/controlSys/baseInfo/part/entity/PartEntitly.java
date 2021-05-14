package com.jeebase.system.controlSys.baseInfo.part.entity;

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
@TableName("part")
public class PartEntitly implements Serializable {
    /**
     * part_id
     */
    @TableId("part_id")
    private String partId;

    /**
     * 主计划id
     */
    private String planId;

    /**
     * 主计划编码
     */
    private String planCode;

    /**
     * 零件编号
     */
    @NotBlank(message="零件编号不能为空")
    private String partCode;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @TableField(fill = FieldFill.INSERT, update = "now()")
    private LocalDateTime createTime;

    /**
     * 零件类型
     */
    private String partType;

    /**
     * 零件状态
     */
    private String partState;

    /**
     * 分拣开始时间
     */
    private LocalDateTime startTime;

    /**
     * 分拣结束时间
     */
    private LocalDateTime endTime;

    /**
     * 入框开始时间
     */
    private LocalDateTime rkStartTime;

    /**
     * 入框结束时间
     */
    private LocalDateTime rkEndTime;

    /**
     * 料框编码
     */
    private String frameCode;

    /**
     * 长
     */
    private Integer length;

    /**
     * 宽
     */
    private Integer width;

    /**
     * 高
     */
    private Integer height;

    /**
     * 重量
     */
    private Integer weight;

    /**
     * 分拣类型 0代表机器分拣 1代表人工录入
     */
    private Integer fjType;
}
