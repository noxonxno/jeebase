package com.jeebase.system.controlSys.baseInfo.apiInfo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;


import java.io.Serializable;

@Getter
@Setter
@TableName("api_info")
public class ApiInfoEntitly implements Serializable {
    @TableId("api_id")
    private String apiId;
    /**
     * 接口名称
     */
    private String apiName;
    /**
     * ip地址
     */
    private String apiIp;
    /**
     * 请求路劲
     */
    private String apiUrl;
    /**
     * 提供方
     */
    private String apiSys;
    /**
     * 业务描述
     */
    private String apiNote;
    /**
     * 任务环节
     */
    private String apiTask;
    /**
     * 请求参数
     */
    private String apiParam;
}
