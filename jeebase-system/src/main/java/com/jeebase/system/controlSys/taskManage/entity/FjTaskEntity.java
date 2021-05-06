package com.jeebase.system.controlSys.taskManage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

@TableName("fj_task")
public class FjTaskEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("plan_id")
    private String planId;

    @TableField("plan_code")
    private String planCode;

    @TableField("dxf_url")
    private String dxfUrl;

    @TableField("dxf_type")
    private String dxfType;

    @TableField("print_time")
    private String printTime;

    @TableField("bfj_time")
    private String bfjTime;

    @TableField("sfj_time")
    private String sfjTime;

    @TableField("ftask_state")
    private String ftaskState;

    @TableField("start_time")
    private LocalDateTime startTime;

    @TableField("end_time")
    private LocalDateTime endTime;

    @TableField("create_time")
    private LocalDateTime createTime;

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public void setDxfUrl(String dxfUrl) {
        this.dxfUrl = dxfUrl;
    }

    public void setDxfType(String dxfType) {
        this.dxfType = dxfType;
    }

    public void setPrintTime(String printTime) {
        this.printTime = printTime;
    }

    public void setBfjTime(String bfjTime) {
        this.bfjTime = bfjTime;
    }

    public void setSfjTime(String sfjTime) {
        this.sfjTime = sfjTime;
    }

    public void setFtaskState(String ftaskState) {
        this.ftaskState = ftaskState;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Integer getId() {
        return id;
    }

    public String getPlanId() {
        return planId;
    }

    public String getPlanCode() {
        return planCode;
    }

    public String getDxfUrl() {
        return dxfUrl;
    }

    public String getDxfType() {
        return dxfType;
    }

    public String getPrintTime() {
        return printTime;
    }

    public String getBfjTime() {
        return bfjTime;
    }

    public String getSfjTime() {
        return sfjTime;
    }

    public String getFtaskState() {
        return ftaskState;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }
}
