package com.jeebase.system.controlSys.taskManage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.common.annotation.log.AroundLog;
import com.jeebase.common.base.PageResult;
import com.jeebase.common.base.Result;
import com.jeebase.system.controlSys.api.SortingApi;
import com.jeebase.system.controlSys.reportAction.entity.FjActionEntity;
import com.jeebase.system.controlSys.reportAction.service.IFjActionService;
import com.jeebase.system.controlSys.taskManage.entity.FjTaskEntity;
import com.jeebase.system.controlSys.taskManage.service.IFjTaskService;
import com.jeebase.system.utils.UUIDUtils;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;


@RestController
@RequestMapping("/fj")
public class FjTaskController {

    @Autowired
    private IFjTaskService fjTaskService;

    @Autowired
    private IFjActionService fjActionService;

    @Autowired
    private SortingApi sortingApi;

    /**
     * 按条件查询列表
     * @param fjTaskEntity,page
     * @return
     */
    @GetMapping("/list")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "查询分拣任务")
    public PageResult<FjTaskEntity> selectFjTaskList(FjTaskEntity fjTaskEntity,Page<FjTaskEntity> page){

        Page<FjTaskEntity> fjTaskEntityPage = fjTaskService.selectList(page, fjTaskEntity);
        return new PageResult<>(fjTaskEntityPage.getTotal(), fjTaskEntityPage.getRecords());
    }

    /**
     * 添加通知
     */
    @PostMapping("/create")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "添加分拣任务")
    @AroundLog(name = "添加分拣任务")
    public Result<FjTaskEntity> create(@RequestBody FjTaskEntity fjTaskEntity) {

        boolean result = fjTaskService.save(fjTaskEntity);
        if (result) {
            return new Result<FjTaskEntity>().success("添加成功").put(fjTaskEntity);
        } else {
            return new Result<FjTaskEntity>().error("添加失败，请重试");
        }
    }


    /**
     * 修改通知
     */
    @PostMapping("/update")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "更新分拣任务")
    @AroundLog(name = "更新分拣任务")
    public Result<FjTaskEntity> update(@RequestBody FjTaskEntity fjTaskEntity) {

        boolean result = fjTaskService.updateById(fjTaskEntity);
        if (result) {
            return new Result<FjTaskEntity>().success("修改成功").put(fjTaskEntity);
        } else {
            return new Result<FjTaskEntity>().error("修改失败");
        }
    }


    /**
     * 删除通知
     */
    @PostMapping("/delete/{fjTaskId}")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "删除分拣任务")
    @AroundLog(name = "删除分拣任务")
    @ApiImplicitParam(paramType = "path", name = "fjTaskId", value = "通知id", required = true, dataType = "String")
    public Result<?> delete(@PathVariable("fjTaskId") String fjTaskId) {

        boolean result = fjTaskService.removeById(fjTaskId);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }

    /**
     * 执行分拣任务
     */
    @PostMapping("/do/{fjTaskId}/{fjState}")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "执行分拣任务")
    @AroundLog(name = "执行分拣任务")
    @ApiImplicitParam(paramType = "path", name = "FjTaskId", value = "通知id", required = true, dataType = "String")
    public Result<?> doTask(@PathVariable("fjTaskId") String fjTaskId,@PathVariable("fjState") String fjState){

        //调用api执行分拣任务
        sortingApi.doFJPlan("");

        //创建初始报工对象
        FjActionEntity fjActionEntity = new FjActionEntity();
        //设置id
        fjActionEntity.setId(UUID.randomUUID().toString());
        //设置指令发送时间
        LocalDateTime now = LocalDateTime.now();
        fjActionEntity.setSendTime(now);
        //设置分拣动作类型 0：大件 1：小件 2：喷码
        fjActionEntity.setActionName(fjState);

        fjActionService.save(fjActionEntity);

        //更改任务执行状态
        FjTaskEntity fjTaskEntity = new FjTaskEntity();
        fjTaskEntity.setId(fjTaskId);
        fjTaskEntity.setFtaskState("1");//0:等待执行,1:正在执行,2:执行完成,3:执行错误
        fjTaskService.updateById(fjTaskEntity);
        return new Result<>().success();
    }
}
