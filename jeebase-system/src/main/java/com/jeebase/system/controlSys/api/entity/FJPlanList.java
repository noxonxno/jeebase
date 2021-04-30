package com.jeebase.system.controlSys.api.entity;

import lombok.Data;

@Data
public class FJPlanList {

    //计划编号/任务编号
    String mission_no;

    //套料图编号
    String casing_no;

    //套料图型号
    String casing_model;

    //0成功、1下载DXF失败、2解析DXF失败、3解析json失败、
    String result;

    //错误或异常信息
    String err_msg;

    //预估喷码时间
    String print_time;

    //预估小件分拣时间
    String sfj_time;

    //预估大件分拣时间
    String bfj_time;
}
