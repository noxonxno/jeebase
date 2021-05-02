package com.jeebase.system.controlSys.api.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.List;

@Data
public class Steel {
    //主键
    private String steel_id;
    //   库位编号
    @TableField(value = "location_code")
    private String steel_place;
    // 钢板型号
    private String steel_model;
    // 钢板编码
    private String steel_code;
    // 长
    @TableField(value = "length")
    private int steel_length;
    // 宽
    @TableField(value = "width")
    private int steel_width;
    // 高
    @TableField(value = "height")
    private int steel_height;
    //重量
    @TableField(value = "weight")
    private int steel_weight;

    @TableField(exist = false)
    private List<Steel> steel_list;
};
