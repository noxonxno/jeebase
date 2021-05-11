package com.jeebase.system.controlSys.reportAction.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.common.annotation.log.AroundLog;
import com.jeebase.common.base.PageResult;
import com.jeebase.common.base.Result;
import com.jeebase.system.controlSys.reportAction.entity.RollerActionEntity;
import com.jeebase.system.controlSys.reportAction.service.IRollerActionService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("rollerAction")
public class RollerActionController {
    @Autowired
    private IRollerActionService rollerActionService;
    /**
     * 查询辊道报工
     * @param
     * @return
     */
    @PostMapping("/list")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "查询辊道报工", notes = "查询辊道报工")
    @ApiImplicitParam(paramType = "query", name = "RollerActionEntity", value = "辊道报工对象", required = false, dataType = "RollerActionEntity")
    public PageResult<RollerActionEntity> queryMesPlan(RollerActionEntity rollerActionEntity, Page<RollerActionEntity> page) {
        Page<RollerActionEntity> rollerActionEntityPage = rollerActionService.selectList(page, rollerActionEntity);
        return new PageResult<>(rollerActionEntityPage.getTotal(),rollerActionEntityPage.getRecords());
    }

    /**
     * 添加辊道报工
     */
    @PostMapping("/save")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "添加辊道报工")
    @AroundLog(name = "添加辊道报工")
    public Result<RollerActionEntity> save(@RequestBody RollerActionEntity rollerActionEntity) {

        boolean result = rollerActionService.save(rollerActionEntity);
        if (result) {
            return new Result<RollerActionEntity>().success("修改成功").put(rollerActionEntity);
        } else {
            return new Result<RollerActionEntity>().error("修改失败");
        }
    }


    /**
     * 修改辊道报工
     */
    @PostMapping("/update")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "更新辊道报工")
    @AroundLog(name = "更新辊道报工")
    public Result<RollerActionEntity> update(@RequestBody RollerActionEntity rollerActionEntity) {

        boolean result = rollerActionService.updateById(rollerActionEntity);
        if (result) {
            return new Result<RollerActionEntity>().success("修改成功").put(rollerActionEntity);
        } else {
            return new Result<RollerActionEntity>().error("修改失败");
        }
    }


    /**
     * 删除辊道报工
     */
    @PostMapping("/delete/{rollerActionId}")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "删除辊道报工")
    @AroundLog(name = "删除辊道报工")
    @ApiImplicitParam(paramType = "path", name = "wmsActionId", value = "辊道报工id", required = true, dataType = "String")
    public Result<?> delete(@PathVariable("rollerActionId") String rollerActionId) {

        boolean result = rollerActionService.removeById(rollerActionId);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }
}
