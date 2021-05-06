package com.jeebase.system.controlSys.taskManage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

@TableName("fjTask_result")
public class FjTaskResultEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("plan_id")
    private String planId;

    @TableField("plan_code")
    private String planCode;

    @TableField("picking_type")
    private String pickingType;

    @TableField("plan_wash_time")
    private String planWashTime;

    @TableField("plan_parts_num")
    private String planPartsNum;

    @TableField("parts_num")
    private String partsNum;

    @TableField("is_succeed")
    private String isSucceed;

    @TableField("tray_id")
    private Date trayId;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("start_time")
    private LocalDateTime startTime;

    @TableField("end_time")
    private LocalDateTime endTime;

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

    public String getPickingType() {
        return pickingType;
    }

    public void setPickingType(String pickingType) {
        this.pickingType = pickingType;
    }

    public String getPlanWashTime() {
        return planWashTime;
    }

    public void setPlanWashTime(String planWashTime) {
        this.planWashTime = planWashTime;
    }

    public String getPlanPartsNum() {
        return planPartsNum;
    }

    public void setPlanPartsNum(String planPartsNum) {
        this.planPartsNum = planPartsNum;
    }

    public String getPartsNum() {
        return partsNum;
    }

    public void setPartsNum(String partsNum) {
        this.partsNum = partsNum;
    }

    public String getIsSucceed() {
        return isSucceed;
    }

    public void setIsSucceed(String isSucceed) {
        this.isSucceed = isSucceed;
    }

    public Date getTrayId() {
        return trayId;
    }

    public void setTrayId(Date trayId) {
        this.trayId = trayId;
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
