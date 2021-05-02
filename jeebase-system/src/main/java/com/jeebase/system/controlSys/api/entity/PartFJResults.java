package com.jeebase.system.controlSys.api.entity;

import lombok.Data;

@Data
public class PartFJResults {
    //计划编码
    private String plan_code;
    //零件编码
    private String part_code;
    //0是小件，1是大件
    private String part_type;
    // 零件型号
    private String part_model;
    //0分拣成功
    private String success;
    // 捡料开始时间
    private String fj_start_time;
    //捡料结束时间
    private String fj_end_time;
    // 入料框开始时间
    private String rk_start_time;
    //入料框结束时间
    private String rk_end_time;
    //料框编码
    private String frame_code;
    //小件分拣完成1，未完成0
    private String spart_complet;
}
