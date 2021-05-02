package com.jeebase.system.controlSys.api.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PlanFJResults {
    //任务编号
    private String mission_no;
    //钢板工位号
    private String station_no;
    //套料图号
    private String casing_no;
    //钢板编号，每张钢板的唯一编号
    private String plate_no;
    //分拣类型，1小件、2中件、3大件
    private int steel_part_type;
    //当前钢板工位的需分拣工件总数量
    private int total;
    //成功分拣数量
    private int success;
    //分拣开始时间
    private Date start_time;
    //分拣结束时间
    private Date end_time;

    private List<SortedPartList> sorted_part_list;
}
