package com.jeebase.system.controlSys.planManage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("")
public class MesDoPlanEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("steel_code")
    private String steelCode;

    @TableField("task_type")
    private String taskType;

    @TableField("place")
    private String place;

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

    @TableField("is_check")
    private String isCheck;

    @TableField("create_time")
    private String createTime;

    @TableField("update_time")
    private String updateTime;

    @TableField("emp_re_id")
    private String empId;

    @TableField("tary_id")
    private String taryId;

    @TableField("err_code")
    private String errCode;

    @TableField("plan_slab_source")
    private String planSlabSource;

    public String getMesAdviceId() {
        return mesAdviceId;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSteelCode(String steelCode) {
        this.steelCode = steelCode;
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

    public void setIsCheck(String isCheck) {
        this.isCheck = isCheck;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public void setTaryId(String taryId) {
        this.taryId = taryId;
    }

    public void setErrCode(String errCode) {
        this.errCode = errCode;
    }

    public void setPlanSlabSource(String planSlabSource) {
        this.planSlabSource = planSlabSource;
    }

    public void setMesAdviceId(String mesAdviceId) {
        this.mesAdviceId = mesAdviceId;
    }

    public Integer getId() {
        return id;
    }

    public String getSteelCode() {
        return steelCode;
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

    public String getIsCheck() {
        return isCheck;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public String getEmpId() {
        return empId;
    }

    public String getTaryId() {
        return taryId;
    }

    public String getErrCode() {
        return errCode;
    }

    public String getPlanSlabSource() {
        return planSlabSource;
    }
}
