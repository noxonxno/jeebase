package com.jeebase.system.controlSys.taskManage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.common.annotation.log.AroundLog;
import com.jeebase.common.base.PageResult;
import com.jeebase.common.base.Result;
import com.jeebase.system.controlSys.api.CutAnalysisApi;
import com.jeebase.system.controlSys.reportAction.entity.CutActionEntity;
import com.jeebase.system.controlSys.reportAction.service.ICutActionService;
import com.jeebase.system.controlSys.taskManage.entity.CutTaskEntity;
import com.jeebase.system.controlSys.taskManage.service.ICutTaskService;
import com.jeebase.system.utils.LocalDateTimeUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;


@RestController
@RequestMapping("/cut")
public class CutTaskController {

    @Autowired
    private ICutTaskService cutTaskService;

    @Autowired
    private ICutActionService cutActionService;

    @Autowired
    private CutAnalysisApi cutAnalysisApi;

    /**
     * 按条件查询列表
     * @param cutTaskEntity
     * @return
     */
    @GetMapping("/list")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "查询切割任务")
    public PageResult<CutTaskEntity> selectFjTaskList(CutTaskEntity cutTaskEntity, Page<CutTaskEntity> page){
        Page<CutTaskEntity> cutTaskEntityPage = cutTaskService.selectList(page, cutTaskEntity);
        return new PageResult<>(cutTaskEntityPage.getTotal(), cutTaskEntityPage.getRecords());
    }

    /**
     * 添加通知
     */
    @PostMapping("/create")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "添加切割任务")
    @AroundLog(name = "添加切割任务")
    public Result<CutTaskEntity> create(@RequestBody CutTaskEntity cutTaskEntity) {

        boolean result = cutTaskService.save(cutTaskEntity);
        if (result) {
            return new Result<CutTaskEntity>().success("添加成功").put(cutTaskEntity);
        } else {
            return new Result<CutTaskEntity>().error("添加失败，请重试");
        }
    }


    /**
     * 修改通知
     */
    @PostMapping("/update")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "更新切割任务")
    @AroundLog(name = "更新切割任务")
    public Result<CutTaskEntity> update(@RequestBody CutTaskEntity cutTaskEntity) {

        boolean result = cutTaskService.updateById(cutTaskEntity);
        if (result) {
            return new Result<CutTaskEntity>().success("修改成功").put(cutTaskEntity);
        } else {
            return new Result<CutTaskEntity>().error("修改失败");
        }
    }


    /**
     * 删除通知
     */
    @PostMapping("/delete/{cutTaskId}")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "删除切割任务")
    @AroundLog(name = "删除切割任务")
    @ApiImplicitParam(paramType = "path", name = "cutTaskId", value = "通知id", required = true, dataType = "String")
    public Result<?> delete(@PathVariable("cutTaskId") String cutTaskId) {

        boolean result = cutTaskService.removeById(cutTaskId);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }

    /**
     * 执行切割任务
     */
    @PostMapping("/do/{cutTaskId}")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "执行切割任务")
    @AroundLog(name = "执行切割任务")
    @ApiImplicitParam(paramType = "path", name = "cutTaskId", value = "通知id", required = true, dataType = "String")
    public Result<?> doTask(@PathVariable("cutTaskId") String cutTaskId){

        //调用api开始任务执行
        cutAnalysisApi.doCutPlan("");

        //创建初始报工记录，并入库
        CutActionEntity cutActionEntity = new CutActionEntity();
        cutActionEntity.setId(UUID.randomUUID().toString());
        //设置指令发送时间
        LocalDateTime now = LocalDateTime.now();
        cutActionEntity.setSendTime(now);
        //设置动作名称
        //设置指令接口

        cutActionService.save(cutActionEntity);

        //更改任务执行状态
        CutTaskEntity cutTaskEntity = new CutTaskEntity();
        cutTaskEntity.setId(cutTaskId);
        cutTaskEntity.setCtaskState("1");
        cutTaskService.updateById(cutTaskEntity);

        return new Result<>().success();
    }
}
