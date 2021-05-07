package com.jeebase.system.controlSys.taskManage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.common.annotation.log.AroundLog;
import com.jeebase.common.base.PageResult;
import com.jeebase.common.base.Result;
import com.jeebase.system.controlSys.api.WMSApi;
import com.jeebase.system.controlSys.reportAction.entity.WmsActionEntity;
import com.jeebase.system.controlSys.reportAction.service.IWmsActionService;
import com.jeebase.system.controlSys.taskManage.entity.CutTaskEntity;
import com.jeebase.system.controlSys.taskManage.entity.WmsTaskEntity;
import com.jeebase.system.controlSys.taskManage.service.IWmsTaskService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.UUID;

@RestController
@RequestMapping("/wms")
public class WmsTaskController {

    @Autowired
    private IWmsTaskService wmsTaskService;

    @Autowired
    private IWmsActionService wmsActionService;

    @Autowired
    private WMSApi wmsApi;

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
    @PostMapping("/delete/{wsTaskId}")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "删除上料任务")
    @AroundLog(name = "删除上料任务")
    @ApiImplicitParam(paramType = "path", name = "wsTaskId", value = "通知id", required = true, dataType = "String")
    public Result<?> delete(@PathVariable("wsTaskId") String wmsTaskId) {

        boolean result = wmsTaskService.removeById(wmsTaskId);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }

    /**
     * 执行上下料任务
     */
    @PostMapping("/do/{wmsTaskId}")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "执行上下料任务")
    @AroundLog(name = "执行上下料任务")
    @ApiImplicitParam(paramType = "path", name = "wmsTaskId", value = "通知id", required = true, dataType = "String")
    public Result<?> doTask(@PathVariable("wmsTaskId") String wmsTaskId){

        //调用api执行上下料任务
        wmsApi.doWmsPlan(new ArrayList<String>());

        //创建初始报工对象
        WmsActionEntity wmsActionEntity = new WmsActionEntity();
        wmsActionEntity.setId(UUID.randomUUID().toString());
        //设置指令发送时间
        LocalDateTime now = LocalDateTime.now();
        wmsActionEntity.setSendTime(now);
        //报工对象入库
        wmsActionService.save(wmsActionEntity);

        //更改任务执行状态
        WmsTaskEntity wmsTaskEntity = new WmsTaskEntity();
        wmsTaskEntity.setId(wmsTaskId);
        wmsTaskEntity.setFplanState("1");
        wmsTaskService.updateById(wmsTaskEntity);

        return new Result<>().success();
    }
}
