package com.jeebase.system.controlSys.taskManage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;


import java.io.Serializable;
import java.sql.Date;

@TableName("wms_task")
public class WmsTaskEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("plan_id")
    private String planId;

    @TableField("plan_code")
    private String planCode;

    @TableField("steel_type")
    private String steelType;

    @TableField("wsm_time")
    private String wsmTime;

    @TableField("location_code")
    private String locationTode;

    @TableField("fplan_state")
    private String fplanState;

    @TableField("create_time")
    private String createTime;

    @TableField("start_time")
    private Date startTime;

    @TableField("end_time")
    private Date endTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public String getLocationTode() {
        return locationTode;
    }

    public void setLocationTode(String locationTode) {
        this.locationTode = locationTode;
    }

    public String getFplanState() {
        return fplanState;
    }

    public void setFplanState(String fplanState) {
        this.fplanState = fplanState;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
}
