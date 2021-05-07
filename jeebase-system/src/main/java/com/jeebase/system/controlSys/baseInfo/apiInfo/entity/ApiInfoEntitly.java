package com.jeebase.system.controlSys.baseInfo.apiInfo.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.NotBlank;


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
    @NotBlank(message="接口名称不能为空")
    private String apiName;
    /**
     * ip地址
     */
    @NotBlank(message="ip地址不能为空")
    private String apiIp;
    /**
     * 请求路劲
     */
    @NotBlank(message="请求路劲不能为空")
    private String apiUrl;
    /**
     * 提供方
     */
    @NotBlank(message="提供方不能为空")
    private String apiSys;
    /**
     * 业务描述
     */
    @NotBlank(message="业务描述不能为空")
    private String apiNote;
    /**
     * 任务环节
     */
    @NotBlank(message="任务环节不能为空")
    private String apiTask;
    /**
     * 请求参数
     */
    private String apiParam;
}
