package com.jeebase.system.controlSys.log.entitly;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author DELL
 */
@Getter
@Setter
@TableName("task_log")
public class TaskLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId("plan_log_id")
    /**
     * plan_log_id
     */
    private String planLogId;

    /**
     * 主计划id
     */
    private String planId;

    /**
     * 主计划编码
     */
    private String planCode;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    private LocalDateTime updataTime;

    /**
     * 切割任务id
     */
    private String ctaskId;

    /**
     * 分拣任务id
     */
    private String ftaskId;

    /**
     * 上下料任务id
     */
    private String wtaskId;

    /**
     * 钢板型号
     */
    private String steelType;

    /**
     * 执行状态
     */
    private String statues;

    /**
     * 任务环节
     */
    private String planStep;

    /**
     * 切割总耗时
     */
    private Long ctaskTime;

    /**
     * 上料总耗时
     */
    private Long wtaskTime;

    /**
     * 分拣总耗时
     */
    private Long ftaskTime;
}
