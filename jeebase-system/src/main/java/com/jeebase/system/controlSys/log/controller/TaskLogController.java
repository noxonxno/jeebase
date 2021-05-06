package com.jeebase.system.controlSys.log.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.common.base.PageResult;
import com.jeebase.common.base.Result;
import com.jeebase.system.controlSys.log.entitly.TaskLog;
import com.jeebase.system.controlSys.log.service.TaskLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author DELL
 */
@RestController
@RequestMapping("/tasklog")
public class TaskLogController {
    @Autowired
    private TaskLogService taskLogService;

    /**
     * 查询所有
     *
     * @param page
     * @param taskLog
     * @return
     */
    @RequestMapping("/listTaskLog")
    public PageResult<TaskLog> listTaskLog(@ApiIgnore Page<TaskLog> page, TaskLog taskLog) {
        Page<TaskLog> taskLogPage = taskLogService.listTaskLog(taskLog, page);
        PageResult<TaskLog> pageResult = new PageResult<>(taskLogPage.getTotal(), taskLogPage.getRecords());
        return pageResult;
    }


    /**
     * 通过id查找
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "getById", method = RequestMethod.POST)
    public Result<?> getById(String id) {
        return new Result<TaskLog>().success().put(taskLogService.getById(id));
    }

    /**
     * 新增
     * @param taskLog
     * @return
     */
    @RequestMapping("insertTaskLog")
    public Result<?> insertTaskLog(TaskLog taskLog) {
        boolean res = taskLogService.insertTaskLog(taskLog);
        return new Result<>().success(res == true ? "新增成功" : "新增失败");
    }

    /**
     * 修改
     * @param taskLog
     * @return
     */
    @RequestMapping("updateTaskLog")
    public Result<?> updateTaskLog(TaskLog taskLog) {
        boolean res = taskLogService.updateTaskLog(taskLog);
        return new Result<>().success(res == true ? "修改成功" : "修改失败");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("deleteTaskLog")
    public Result<?> deleteTaskLog(String id) {
        boolean res = taskLogService.deleteTaskLog(id);
        return new Result<>().success(res == true ? "删除成功" : "删除失败");
    }


}
