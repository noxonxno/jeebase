package com.jeebase.system.controlSys.planManage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;


@TableName("mes_plan")
public class MesPlanEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @TableField("steel_code")
    private String steelCode;

    @TableField("plan_code")
    private String planCode;

    @TableField("mes_advice_id")
    private String mesAdviceId;

    @TableField("steel_type")
    private String steelType;

    @TableField("dxf_url")
    private String dxfUrl;

    @TableField("dxf_model")
    private String dxfModel;

    @TableField("dxf_code")
    private String dxfCode;

    @TableField("dxf_json")
    private String dxfJson;

    @TableField("sfj_time")
    private String sfjTime;

    @TableField("bfj_time")
    private String bfjTime;

    @TableField("print_time")
    private String printTime;

    @TableField("cut_code")
    private String cutCode;

    @TableField("cut_model")
    private String cutModel;

    @TableField("cut_time")
    private String cutTime;

    @TableField("cut_check")
    private String cutCheck;

    @TableField("fj_check")
    private String fjCheck;

    @TableField("cut_url")
    private String cutUrl;


    @TableField("sort")
    private int sort;

    @TableField("create_time")
    private String createTime;

    @TableField("update_time")
    private String updateTime;

    @TableField("user_id")
    private String userId;

//    @TableField("tary_id")
//    private String taryId;

    @TableField("plan_state")
    private String planState;

    @TableField("err_code")
    private String errCode;

    @TableField("plan_slab_source")
    private String planSlabSource;

    public void setDxfModel(String dxfModel) {
        this.dxfModel = dxfModel;
    }

    public void setDxfCode(String dxfCode) {
        this.dxfCode = dxfCode;
    }

    public void setDxfJson(String dxfJson) {
        this.dxfJson = dxfJson;
    }

    public void setSfjTime(String sfjTime) {
        this.sfjTime = sfjTime;
    }

    public void setBfjTime(String bfjTime) {
        this.bfjTime = bfjTime;
    }

    public void setPrintTime(String printTime) {
        this.printTime = printTime;
    }

    public void setCutCode(String cutCode) {
        this.cutCode = cutCode;
    }

    public void setCutModel(String cutModel) {
        this.cutModel = cutModel;
    }

    public void setCutTime(String cutTime) {
        this.cutTime = cutTime;
    }

    public void setCutCheck(String cutCheck) {
        this.cutCheck = cutCheck;
    }

    public void setFjCheck(String fjCheck) {
        this.fjCheck = fjCheck;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPlanState(String planState) {
        this.planState = planState;
    }

    public String getDxfModel() {
        return dxfModel;
    }

    public String getDxfCode() {
        return dxfCode;
    }

    public String getDxfJson() {
        return dxfJson;
    }

    public String getSfjTime() {
        return sfjTime;
    }

    public String getBfjTime() {
        return bfjTime;
    }

    public String getPrintTime() {
        return printTime;
    }

    public String getCutCode() {
        return cutCode;
    }

    public String getCutModel() {
        return cutModel;
    }

    public String getCutTime() {
        return cutTime;
    }

    public String getCutCheck() {
        return cutCheck;
    }

    public String getFjCheck() {
        return fjCheck;
    }

    public int getSort() {
        return sort;
    }

    public String getUserId() {
        return userId;
    }

    public String getPlanState() {
        return planState;
    }

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


    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }


//    public void setTaryId(String taryId) {
//        this.taryId = taryId;
//    }

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


    public String getCreateTime() {
        return createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }


//    public String getTaryId() {
//        return taryId;
//    }

    public String getErrCode() {
        return errCode;
    }

    public String getPlanSlabSource() {
        return planSlabSource;
    }

    public void setPlanCode(String planCode) {
        this.planCode = planCode;
    }

    public String getPlanCode() {
        return planCode;
    }

}
