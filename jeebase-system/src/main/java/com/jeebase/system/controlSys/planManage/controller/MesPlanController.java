package com.jeebase.system.controlSys.planManage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.common.annotation.log.AroundLog;
import com.jeebase.common.base.PageResult;
import com.jeebase.common.base.Result;
import com.jeebase.system.common.dto.DictInfo;
import com.jeebase.system.controlSys.planManage.entity.MesAdviceEntity;
import com.jeebase.system.controlSys.planManage.entity.MesPlanEntity;
import com.jeebase.system.controlSys.planManage.service.IMesPlanService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("mesPlan")
public class MesPlanController {

    @Autowired
    private IMesPlanService mesPlanService;


    /**
     * 查询计划
     * @param
     * @return
     */
    @PostMapping("/list")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "查询mes计划", notes = "查询mes计划")
    @ApiImplicitParam(paramType = "query", name = "mesPlan", value = "mes计划对象", required = false, dataType = "MesPlan")
    public PageResult<MesPlanEntity> queryMesPlan(MesPlanEntity mesPlanEntity, Page<MesPlanEntity> page) {
        Page<MesPlanEntity> mesPlanEntityPage = mesPlanService.selectList(page, mesPlanEntity);
        return new PageResult<>(mesPlanEntityPage.getTotal(),mesPlanEntityPage.getRecords());
    }


    /**
     * 添加计划
     */
    @PostMapping("/save")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "添加mes计划")
    @AroundLog(name = "添加mes计划")
    public Result<MesPlanEntity> save(@RequestBody MesPlanEntity mesPlanEntity) {

        boolean result = mesPlanService.save(mesPlanEntity);
        if (result) {
            return new Result<MesPlanEntity>().success("修改成功").put(mesPlanEntity);
        } else {
            return new Result<MesPlanEntity>().error("修改失败");
        }
    }


    /**
     * 修改计划
     */
    @PostMapping("/update")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "更新查询mes计划")
    @AroundLog(name = "更新查询mes计划")
    public Result<MesPlanEntity> update(@RequestBody MesPlanEntity mesPlanEntity) {

        boolean result = mesPlanService.updateById(mesPlanEntity);
        if (result) {
            return new Result<MesPlanEntity>().success("修改成功").put(mesPlanEntity);
        } else {
            return new Result<MesPlanEntity>().error("修改失败");
        }
    }


    /**
     * 删除计划
     */
    @PostMapping("/delete/{mesPlanId}")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "删除mes计划")
    @AroundLog(name = "删除mes计划")
    @ApiImplicitParam(paramType = "path", name = "mesPlanId", value = "通知id", required = true, dataType = "String")
    public Result<?> delete(@PathVariable("mesPlanId") String mesAdviceId) {

        boolean result = mesPlanService.removeById(mesAdviceId);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }

}
