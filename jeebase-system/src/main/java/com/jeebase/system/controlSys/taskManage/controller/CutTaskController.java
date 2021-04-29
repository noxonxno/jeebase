package com.jeebase.system.controlSys.taskManage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.common.annotation.log.AroundLog;
import com.jeebase.common.base.PageResult;
import com.jeebase.common.base.Result;
import com.jeebase.system.controlSys.taskManage.entity.CutTaskEntity;
import com.jeebase.system.controlSys.taskManage.service.ICutTaskService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("")
public class CutTaskController {

    @Autowired
    private ICutTaskService cutTaskService;

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
    @PostMapping("/delete/{mesAdviceId}")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "删除切割任务")
    @AroundLog(name = "删除切割任务")
    @ApiImplicitParam(paramType = "path", name = "mesAdviceId", value = "通知id", required = true, dataType = "String")
    public Result<?> delete(@PathVariable("mesAdviceId") String mesAdviceId) {

        boolean result = cutTaskService.removeById(mesAdviceId);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }
}
