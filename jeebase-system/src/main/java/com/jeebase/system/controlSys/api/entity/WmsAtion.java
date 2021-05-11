package com.jeebase.system.controlSys.api.entity;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class WmsAtion {
    //任务ID（唯一）
    private String task_id;

    //计划编码
    private String plan_code;

    //任务类型，固定值：1上料，2下料
    private String task_tpye;

    //固定值：0取消，1成功，2失败
    private String task_status;

    //失败原因
    private String fail_msg;

    //任务开始时间
    private LocalDateTime task_sTime;

    //任务结束时间
    private LocalDateTime task_eTime;
}

