package com.jeebase.system.controlSys.taskManage.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.common.annotation.log.AroundLog;
import com.jeebase.common.base.PageResult;
import com.jeebase.common.base.Result;
import com.jeebase.system.controlSys.taskManage.entity.FjTaskResultEntity;
import com.jeebase.system.controlSys.taskManage.service.IFjTaskResultService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/fjresult")
public class FjTaskResultController {

    @Autowired
    private IFjTaskResultService fjTaskResultService;

    /**
     * 按条件查询列表
     * @param fjTaskResultEntity,page
     * @return
     */
    @GetMapping("/list")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "查询分拣任务结果")
    public PageResult<FjTaskResultEntity> selectFjTaskList(FjTaskResultEntity fjTaskResultEntity,Page<FjTaskResultEntity> page){

        Page<FjTaskResultEntity> fjTaskResultEntityPage = fjTaskResultService.selectList(page, fjTaskResultEntity);
        return new PageResult<>(fjTaskResultEntityPage.getTotal(), fjTaskResultEntityPage.getRecords());
    }

    /**
     * 添加通知
     */
    @PostMapping("/create")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "添加分拣任务结果")
    @AroundLog(name = "添加分拣任务")
    public Result<FjTaskResultEntity> create(@RequestBody FjTaskResultEntity fjTaskResultEntity) {

        boolean result = fjTaskResultService.save(fjTaskResultEntity);
        if (result) {
            return new Result<FjTaskResultEntity>().success("添加成功").put(fjTaskResultEntity);
        } else {
            return new Result<FjTaskResultEntity>().error("添加失败，请重试");
        }
    }


    /**
     * 修改通知
     */
    @PostMapping("/update")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "更新分拣任务结果")
    @AroundLog(name = "更新分拣任务")
    public Result<FjTaskResultEntity> update(@RequestBody FjTaskResultEntity fjTaskResultEntity) {

        boolean result = fjTaskResultService.updateById(fjTaskResultEntity);
        if (result) {
            return new Result<FjTaskResultEntity>().success("修改成功").put(fjTaskResultEntity);
        } else {
            return new Result<FjTaskResultEntity>().error("修改失败");
        }
    }


    /**
     * 删除通知
     */
    @PostMapping("/delete/{fjTaskResultId}")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "删除分拣任务结果")
    @AroundLog(name = "删除分拣任务")
    @ApiImplicitParam(paramType = "path", name = "fjTaskResultId", value = "分拣任务结果id", required = true, dataType = "String")
    public Result<?> delete(@PathVariable("fjTaskResultId") String fjTaskResultId) {

        boolean result = fjTaskResultService.removeById(fjTaskResultId);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }
}
