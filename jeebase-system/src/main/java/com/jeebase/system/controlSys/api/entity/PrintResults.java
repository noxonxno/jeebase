package com.jeebase.system.controlSys.api.entity;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class PrintResults {
    //计划编码
    private String mission_no;
    //钢板工位号
    private String station_no;
    //套料图号
    private String casing_no;
    //钢板编号，每张钢板的唯一编号
    private String plate_no;
//    总打码工件数量
    private int total;
    //成功打码工件数量
    private int success;
    //开始时间
    private Date start_time;
    //结束时间
    private Date end_time;

    private List<PartList> part_list;
}
