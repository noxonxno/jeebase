package com.jeebase.system.controlSys.taskManage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

@TableName("wms_task")
public class WmsTaskEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private String id;

    @TableField("plan_id")
    private String planId;

    @TableField("plan_code")
    private String planCode;

    @TableField("steel_type")
    private String steelType;

    @TableField("wsm_time")
    private String wsmTime;

    @TableField("location_code")
    private String locationCode;

    @TableField("fplan_state")
    private String fplanState;//任务状态0取消，1成功，2失败，3未开始，4执行中

    private String fplanType;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("start_time")
    private LocalDateTime startTime;

    @TableField("end_time")
    private LocalDateTime endTime;

    private String errInfo;

    private String exeModel;

    public String getFplanType() {
        return fplanType;
    }

    public void setFplanType(String fplanType) {
        this.fplanType = fplanType;
    }

    public String getExeModel() {
        return exeModel;
    }

    public void setExeModel(String exeModel) {
        this.exeModel = exeModel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlanId() {
        return planId;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public String getPlanCode() {
        return planCode;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getSteelType() {
        return steelType;
    }

    public void setSteelType(String steelType) {
        this.steelType = steelType;
    }

    public String getWsmTime() {
        return wsmTime;
    }

    public void setWsmTime(String wsmTime) {
        this.wsmTime = wsmTime;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationTode) {
        this.locationCode = locationTode;
    }

    public String getFplanState() {
        return fplanState;
    }

    public void setFplanState(String fplanState) {
        this.fplanState = fplanState;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }
}
