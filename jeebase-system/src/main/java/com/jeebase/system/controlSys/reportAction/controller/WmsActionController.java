package com.jeebase.system.controlSys.reportAction.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.common.annotation.log.AroundLog;
import com.jeebase.common.base.PageResult;
import com.jeebase.common.base.Result;
import com.jeebase.system.controlSys.reportAction.entity.CutActionEntity;
import com.jeebase.system.controlSys.reportAction.entity.WmsActionEntity;
import com.jeebase.system.controlSys.reportAction.service.IWmsActionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("wmsAction")
public class WmsActionController {

    @Autowired
    private IWmsActionService wmsActionService;

    /**
     * 查询切割报工
     * @param
     * @return
     */
    @PostMapping("/list")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "查询上下料报工", notes = "查询上下料报工")
    @ApiImplicitParam(paramType = "query", name = "WmsActionEntity", value = "上下料报工对象", required = false, dataType = "WmsActionEntity")
    public PageResult<WmsActionEntity> queryMesPlan(WmsActionEntity wmsActionEntity, Page<WmsActionEntity> page) {
        Page<WmsActionEntity> wmsActionEntityPage = wmsActionService.selectList(page, wmsActionEntity);
        return new PageResult<>(wmsActionEntityPage.getTotal(),wmsActionEntityPage.getRecords());
    }


    /**
     * 添加切割报工
     */
    @PostMapping("/save")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "添加上下料报工")
    @AroundLog(name = "添加上下料报工")
    public Result<WmsActionEntity> save(@RequestBody WmsActionEntity wmsActionEntity) {

        boolean result = wmsActionService.save(wmsActionEntity);
        if (result) {
            return new Result<WmsActionEntity>().success("修改成功").put(wmsActionEntity);
        } else {
            return new Result<WmsActionEntity>().error("修改失败");
        }
    }


    /**
     * 修改切割报工
     */
    @PostMapping("/update")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "更新上下料报工")
    @AroundLog(name = "更新上下料报工")
    public Result<WmsActionEntity> update(@RequestBody WmsActionEntity wmsActionEntity) {

        boolean result = wmsActionService.updateById(wmsActionEntity);
        if (result) {
            return new Result<WmsActionEntity>().success("修改成功").put(wmsActionEntity);
        } else {
            return new Result<WmsActionEntity>().error("修改失败");
        }
    }


    /**
     * 删除切割报工
     */
    @PostMapping("/delete/{wmsActionId}")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "删除上下料报工")
    @AroundLog(name = "删除上下料报工")
    @ApiImplicitParam(paramType = "path", name = "wmsActionId", value = "上下料报工id", required = true, dataType = "String")
    public Result<?> delete(@PathVariable("wmsActionId") String wmsActionId) {

        boolean result = wmsActionService.removeById(wmsActionId);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }
}
