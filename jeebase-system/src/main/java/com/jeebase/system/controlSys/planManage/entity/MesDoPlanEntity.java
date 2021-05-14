package com.jeebase.system.controlSys.planManage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("mes_doplan")
public class MesDoPlanEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id")
    private String id;

    @TableField("plan_code")
    private String planCode;

    @TableField("task_type")//任务环节
    private int taskType;

    private String planState;//0取消，1成功，2失败，3未开始，4执行中,5等待执行中

    @TableField("place")
    private String place;

    private int cutType;

    @TableField("exe_model")
    private String exeModel;

    @TableField("mes_advice_id")
    private String mesAdviceId;

    @TableField("steel_type")
    private String steelType;

    @TableField("dxf_url")
    private String dxfUrl;

    @TableField("cut_url")
    private String cutUrl;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("tray_id")
    private String trayId;

    private int sort;

    public int getCutType() {
        return cutType;
    }

    public void setCutType(int cutType) {
        this.cutType = cutType;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getMesAdviceId() {
        return mesAdviceId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSteelType(String steelType) {
        this.steelType = steelType;
    }

    public void setDxfUrl(String dxfUrl) {
        this.dxfUrl = dxfUrl;
    }

    public void setCutUrl(String cutUrl) {
        this.cutUrl = cutUrl;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public void setTrayId(String taryId) {
        this.trayId = taryId;
    }

    public void setMesAdviceId(String mesAdviceId) {
        this.mesAdviceId = mesAdviceId;
    }

    public String getId() {
        return id;
    }

    public String getSteelType() {
        return steelType;
    }

    public String getDxfUrl() {
        return dxfUrl;
    }

    public String getCutUrl() {
        return cutUrl;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getPlanCode() {
        return planCode;
    }

    public int getTaskType() {
        return taskType;
    }

    public void setTaskType(int taskType) {
        this.taskType = taskType;
    }

    public String getPlanState() {
        return planState;
    }

    public void setPlanState(String planState) {
        this.planState = planState;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getExeModel() {
        return exeModel;
    }

    public void setExeModel(String exeModel) {
        this.exeModel = exeModel;
    }

    public String getTrayId() {
        return trayId;
    }
}
