package com.jeebase.system.controlSys.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CutTask {
    //    主键
    private String plan_id;
//    主计划ID
    private String ctask_id;
//    主计划编码
    private String plan_code;
//    切割文件地址
    private String cuf_url;
//    切割工位编号
    private String station_id;
//    切割文件型号
    private String slicing_file;
//    切割文件json数据
    private String cut_json;
//    预估切割时间
    private Long cut_time;
//    托盘ID
    private String tray_id;
//    校验标识
    private String is_check;
//    切割任务状态
    private String ctask_state;
//    钢板型号
    private String steel_type;
//    开始时间
    private Date start_time;
//    结束时间
    private Date end_time;

    @TableField(exist = false)
    private List<CutTask> plan_list;


}
