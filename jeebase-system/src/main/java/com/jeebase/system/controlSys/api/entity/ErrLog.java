package com.jeebase.system.controlSys.api.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ErrLog {
//    主键
    private String log_id;
//    异常类型
    private String ep_type;
//    创建时间
    private Date create_time;
//    主计划ID
    private String plan_id;
//    主计划编码
    private String plan_code;
//    异常来源
    private String ep_source;
//    异常详情
    private String ep_details;
//    处理标识
    private String ep_status;
}

