package com.jeebase.system.controlSys.reportAction.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.common.annotation.log.AroundLog;
import com.jeebase.common.base.PageResult;
import com.jeebase.common.base.Result;
import com.jeebase.system.controlSys.reportAction.entity.CutActionEntity;
import com.jeebase.system.controlSys.reportAction.entity.FjActionEntity;
import com.jeebase.system.controlSys.reportAction.service.IFjActionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("fjAction")
public class FjActionController {

    @Autowired
    private IFjActionService fjActionService;

    /**
     * 查询切割报工
     * @param
     * @return
     */
    @PostMapping("/list")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "查询分拣报工", notes = "查询分拣报工")
    @ApiImplicitParam(paramType = "query", name = "fjActionEntity", value = "分拣报工对象", required = false, dataType = "FjActionEntity")
    public PageResult<FjActionEntity> queryMesPlan(FjActionEntity fjActionEntity, Page<FjActionEntity> page) {
        Page<FjActionEntity> fjActionEntityPage = fjActionService.selectList(page, fjActionEntity);
        return new PageResult<>(fjActionEntityPage.getTotal(),fjActionEntityPage.getRecords());
    }


    /**
     * 添加切割报工
     */
    @PostMapping("/save")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "添加分拣报工")
    @AroundLog(name = "添加分拣报工")
    public Result<FjActionEntity> save(@RequestBody FjActionEntity fjActionEntity) {

        boolean result = fjActionService.save(fjActionEntity);
        if (result) {
            return new Result<FjActionEntity>().success("修改成功").put(fjActionEntity);
        } else {
            return new Result<FjActionEntity>().error("修改失败");
        }
    }


    /**
     * 修改切割报工
     */
    @PostMapping("/update")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "更新分拣报工")
    @AroundLog(name = "更新分拣报工")
    public Result<FjActionEntity> update(@RequestBody FjActionEntity fjActionEntity) {

        boolean result = fjActionService.updateById(fjActionEntity);
        if (result) {
            return new Result<FjActionEntity>().success("修改成功").put(fjActionEntity);
        } else {
            return new Result<FjActionEntity>().error("修改失败");
        }
    }


    /**
     * 删除切割报工
     */
    @PostMapping("/delete/{fjActionId}")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "删除分拣报工")
    @AroundLog(name = "删除分拣报工")
    @ApiImplicitParam(paramType = "path", name = "fjActionId", value = "分拣报工id", required = true, dataType = "String")
    public Result<?> delete(@PathVariable("fjActionId") String fjActionId) {

        boolean result = fjActionService.removeById(fjActionId);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }
}
