package com.jeebase.system.controlSys.baseInfo.tary.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * @author DELL
 */
@Getter
@Setter
public class TaryDto {
    /**
     * tary_id 托盘id
     */
    private String taryId;

    /**
     * 主计划编码
     */
    private String planCode;

    /**
     * 托盘信息
     */
    private String taryInfo;

    /**
     *托盘状态 0表示空闲 1表示损坏 2表示正在使用 3表示正在维修
     */
    private Integer taryState;

    /**
     * 托盘使用次数
     */
    private Integer taryNum;

    /**
     * 创建人
     */
    private String createUser;
    /**
     * 修改人
     */
    private String updateUser;


    /**
     * 使用阈值
     */
    private Integer taryFixNum;

    private Integer pageSize;
    private Integer pageNumber;
}
