package com.jeebase.system.controlSys.api.entity;

import lombok.Data;

@Data
public class PartList {

    //工件在钢板上的序号（JSON文件中应该会有，DXF文件由分拣系统自动生成）
    private int part_sn;

    //工件型号
    private String part_model;

    //打码信息
    private String code;

    //打码结果。0失败，1成功
    private int code_result;
}
