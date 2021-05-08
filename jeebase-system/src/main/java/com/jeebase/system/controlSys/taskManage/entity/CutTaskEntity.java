package com.jeebase.system.controlSys.taskManage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDateTime;

@TableName("cut_task")
public class CutTaskEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private String id;

    @TableField("plan_id")
    private String planId;

    @TableField("plan_code")
    private String planCode;

    @TableField("cuf_url")
    private String cufUrl;

    @TableField("steel_type")
    private String steelType;

    public String getSteelType() {
        return steelType;
    }

    public void setSteelType(String steelType) {
        this.steelType = steelType;
    }

    @TableField("station_id")
    private String stationId;

    @TableField("cuf_type")
    private String slicingFileType;

    @TableField("cut_json")
    private String cutJson;

    @TableField("cut_time")
    private Long cutTime;

    @TableField("tray_id")
    private String trayId;

    @TableField("ctask_state")
    private String ctaskState;

    @TableField("start_time")
    private LocalDateTime startTime;

    @TableField("end_time")
    private LocalDateTime endTime;

    @TableField("create_time")
    private LocalDateTime createTime;

    private String exeModel;

    public String getExeModel() {
        return exeModel;
    }

    public void setExeModel(String exeModel) {
        this.exeModel = exeModel;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPlanId(String planId) {
        this.planId = planId;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public void setCufUrl(String cufUrl) {
        this.cufUrl = cufUrl;
    }

    public void setStationId(String stationId) {
        this.stationId = stationId;
    }

    public void setSlicingFileType(String slicingFileType) {
        this.slicingFileType = slicingFileType;
    }

    public void setCutJson(String cutJson) {
        this.cutJson = cutJson;
    }

    public void setCutTime(Long cutTime) {
        this.cutTime = cutTime;
    }

    public void setTrayId(String trayId) {
        this.trayId = trayId;
    }

    public void setCtaskState(String ctaskState) {
        this.ctaskState = ctaskState;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getId() {
        return id;
    }

    public String getPlanId() {
        return planId;
    }

    public String getPlanCode() {
        return planCode;
    }

    public String getCufUrl() {
        return cufUrl;
    }

    public String getStationId() {
        return stationId;
    }

    public String getSlicingFileType() {
        return slicingFileType;
    }

    public String getCutJson() {
        return cutJson;
    }

    public Long getCutTime() {
        return cutTime;
    }

    public String getTrayId() {
        return trayId;
    }

    public String getCtaskState() {
        return ctaskState;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }


}
