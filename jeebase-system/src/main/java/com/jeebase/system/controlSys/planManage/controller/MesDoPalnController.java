package com.jeebase.system.controlSys.planManage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.common.annotation.log.AroundLog;
import com.jeebase.common.base.PageResult;
import com.jeebase.common.base.Result;
import com.jeebase.system.common.dto.DictInfo;
import com.jeebase.system.controlSys.planManage.entity.MesDoPlanEntity;
import com.jeebase.system.controlSys.planManage.entity.MesPlanEntity;
import com.jeebase.system.controlSys.planManage.service.IMesDoPlanService;
import com.jeebase.system.controlSys.planManage.service.IMesPlanService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("mesDoPlan")
public class MesDoPalnController {
    @Autowired
    private IMesDoPlanService mesDoPlanService;

    /**
     * 查询可执行计划
     * @param
     * @return
     */
    @PostMapping("/list")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "查询mes计划", notes = "查询mes可执行计划")
    @ApiImplicitParam(paramType = "query", name = "mesPlan", value = "mes计划对象", required = false, dataType = "MesPlan")
    public PageResult<MesDoPlanEntity> queryMesDoPlan(MesDoPlanEntity mesDoPlanEntity, Page<MesDoPlanEntity> page) {

        Page<MesDoPlanEntity> mesDoPlanEntityPage = mesDoPlanService.selectList(page, mesDoPlanEntity);

        return new PageResult<>(mesDoPlanEntityPage.getTotal(),mesDoPlanEntityPage.getRecords());
    }

    /**
     * 添加计划
     */
    @PostMapping("/save")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "添加mes计划")
    @AroundLog(name = "添加mes计划")
    public Result<MesDoPlanEntity> save(@RequestBody MesDoPlanEntity mesDoPlanEntity) {

        boolean result = mesDoPlanService.save(mesDoPlanEntity);
        if (result) {
            return new Result<MesDoPlanEntity>().success("修改成功").put(mesDoPlanEntity);
        } else {
            return new Result<MesDoPlanEntity>().error("修改失败");
        }
    }


    /**
     * 修改计划
     */
    @PostMapping("/update")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "更新查询mes计划")
    @AroundLog(name = "更新查询mes计划")
    public Result<MesDoPlanEntity> update(@RequestBody MesDoPlanEntity mesDoPlanEntity) {

        boolean result = mesDoPlanService.updateById(mesDoPlanEntity);
        if (result) {
            return new Result<MesDoPlanEntity>().success("修改成功").put(mesDoPlanEntity);
        } else {
            return new Result<MesDoPlanEntity>().error("修改失败");
        }
    }


    /**
     * 删除计划
     */
    @PostMapping("/delete/{mesDoPlanId}")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "删除mes可执行计划")
    @AroundLog(name = "删除mes可执行计划")
    @ApiImplicitParam(paramType = "path", name = "mesDoPlanId", value = "可执行计划id", required = true, dataType = "String")
    public Result<?> delete(@PathVariable("mesDoPlanId") String mesDoPlanId) {

        boolean result = mesDoPlanService.removeById(mesDoPlanId);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }

}
