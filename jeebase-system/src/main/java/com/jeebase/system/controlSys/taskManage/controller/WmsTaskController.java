package com.jeebase.system.controlSys.taskManage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.common.annotation.log.AroundLog;
import com.jeebase.common.base.PageResult;
import com.jeebase.common.base.Result;
import com.jeebase.system.controlSys.taskManage.entity.WmsTaskEntity;
import com.jeebase.system.controlSys.taskManage.service.IWmsTaskService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("")
public class WmsTaskController {

    @Autowired
    private IWmsTaskService wmsTaskService;

    /**
     * 按条件查询列表
     * @param wmsTaskEntity
     * @return
     */
    @GetMapping("/list")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "查询上料任务")
    public PageResult<WmsTaskEntity> selectFjTaskList(WmsTaskEntity wmsTaskEntity, Page<WmsTaskEntity> page){

        Page<WmsTaskEntity> wmsTaskEntityPage = wmsTaskService.selectList(page, wmsTaskEntity);
        return new PageResult<>(wmsTaskEntityPage.getTotal(), wmsTaskEntityPage.getRecords());
    }

    /**
     * 添加通知
     */
    @PostMapping("/create")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "添加上料任务")
    @AroundLog(name = "添加上料任务")
    public Result<WmsTaskEntity> create(@RequestBody WmsTaskEntity wmsTaskEntity) {

        boolean result = wmsTaskService.save(wmsTaskEntity);
        if (result) {
            return new Result<WmsTaskEntity>().success("添加成功").put(wmsTaskEntity);
        } else {
            return new Result<WmsTaskEntity>().error("添加失败，请重试");
        }
    }


    /**
     * 修改通知
     */
    @PostMapping("/update")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "更新上料任务")
    @AroundLog(name = "更新上料任务")
    public Result<WmsTaskEntity> update(@RequestBody WmsTaskEntity wmsTaskEntity) {

        boolean result = wmsTaskService.updateById(wmsTaskEntity);
        if (result) {
            return new Result<WmsTaskEntity>().success("修改成功").put(wmsTaskEntity);
        } else {
            return new Result<WmsTaskEntity>().error("修改失败");
        }
    }


    /**
     * 删除通知
     */
    @PostMapping("/delete/{mesAdviceId}")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "删除上料任务")
    @AroundLog(name = "删除上料任务")
    @ApiImplicitParam(paramType = "path", name = "mesAdviceId", value = "通知id", required = true, dataType = "String")
    public Result<?> delete(@PathVariable("mesAdviceId") String wmsTaskId) {

        boolean result = wmsTaskService.removeById(wmsTaskId);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }
}
