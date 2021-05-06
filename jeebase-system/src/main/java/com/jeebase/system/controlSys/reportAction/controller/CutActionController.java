package com.jeebase.system.controlSys.reportAction.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.common.annotation.log.AroundLog;
import com.jeebase.common.base.PageResult;
import com.jeebase.common.base.Result;
import com.jeebase.system.controlSys.reportAction.entity.CutActionEntity;
import com.jeebase.system.controlSys.reportAction.service.ICutActionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cutAction")
public class CutActionController {

    @Autowired
    private ICutActionService cutActionService;


    /**
     * 查询切割报工
     * @param
     * @return
     */
    @PostMapping("/list")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "查询切割报工", notes = "查询切割报工")
    @ApiImplicitParam(paramType = "query", name = "CutActionEntity", value = "切割报工对象", required = false, dataType = "CutActionEntity")
    public PageResult<CutActionEntity> queryMesPlan(CutActionEntity cutActionEntity, Page<CutActionEntity> page) {
        Page<CutActionEntity> cutActionEntityPage = cutActionService.selectList(page, cutActionEntity);
        return new PageResult<>(cutActionEntityPage.getTotal(),cutActionEntityPage.getRecords());
    }


    /**
     * 添加切割报工
     */
    @PostMapping("/save")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "添加切割报工")
    @AroundLog(name = "添加切割报工")
    public Result<CutActionEntity> save(@RequestBody CutActionEntity cutActionEntity) {

        boolean result = cutActionService.save(cutActionEntity);
        if (result) {
            return new Result<CutActionEntity>().success("修改成功").put(cutActionEntity);
        } else {
            return new Result<CutActionEntity>().error("修改失败");
        }
    }


    /**
     * 修改切割报工
     */
    @PostMapping("/update")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "更新切割报工")
    @AroundLog(name = "更新切割报工")
    public Result<CutActionEntity> update(@RequestBody CutActionEntity cutActionEntity) {

        boolean result = cutActionService.updateById(cutActionEntity);
        if (result) {
            return new Result<CutActionEntity>().success("修改成功").put(cutActionEntity);
        } else {
            return new Result<CutActionEntity>().error("修改失败");
        }
    }


    /**
     * 删除切割报工
     */
    @PostMapping("/delete/{cutActionId}")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "删除切割报工")
    @AroundLog(name = "删除切割报工")
    @ApiImplicitParam(paramType = "path", name = "cutActionId", value = "切割报工id", required = true, dataType = "String")
    public Result<?> delete(@PathVariable("cutActionId") String cutActionId) {

        boolean result = cutActionService.removeById(cutActionId);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }
}
