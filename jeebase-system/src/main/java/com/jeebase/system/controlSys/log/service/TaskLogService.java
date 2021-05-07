package com.jeebase.system.controlSys.log.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.controlSys.log.entitly.TaskLog;

/**
 * @author DELL
 */
public interface TaskLogService extends IService<TaskLog> {
    /**
     * 分页查询所有
     *
     * @param taskLog
     * @param page
     * @return
     */
    Page<TaskLog> listTaskLog(TaskLog taskLog, Page<TaskLog> page);

    /**
     * +
     * 通过id获取
     *
     * @param id
     * @return
     */
    TaskLog getById(String id);

    /**
     * 新增
     *
     * @param taskLog
     * @return
     */
    boolean insertTaskLog(TaskLog taskLog);

    /**
     * 修改
     *
     * @param taskLog
     * @return
     */
    boolean updateTaskLog(TaskLog taskLog);

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    boolean deleteTaskLog(String id);

}
