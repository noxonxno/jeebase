package com.jeebase.system.controlSys.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;


import java.util.List;

@Data
public class CutAtion {
    //计划编码
    private String plan_code;
    //主键
    private String action_id;
    //动作名称
    private String action_name;
    //结果
    private String result;
    //指令接口
    private String send_log;
    //总零件数量
    @TableField(exist = false)
    private String part_total;
    //实际切割零件数量
    @TableField(exist = false)
    private String part_succtotal;
    //开始时间
    private String start_time;
    //结束时间
    private String end_time;
    //切割失败零件集合
    @TableField(exist = false)
    private List<Cutfail>   cutfail_list;

}
