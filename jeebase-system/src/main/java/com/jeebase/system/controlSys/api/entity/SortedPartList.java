package com.jeebase.system.controlSys.api.entity;

import lombok.Data;

@Data
public class SortedPartList {
    //已分拣的工件型号
    private String part_model;
    //已分拣的工件型号数量
    private int part_quantity;
}
