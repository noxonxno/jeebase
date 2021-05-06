package com.jeebase.system.controlSys.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.system.controlSys.log.entitly.TaskLog;
import com.jeebase.system.controlSys.log.mapper.TaskLogMapper;
import com.jeebase.system.controlSys.log.service.TaskLogService;
import com.jeebase.system.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author DELL
 */
@Service
public class TaskLogServiceImpl extends ServiceImpl<TaskLogMapper, TaskLog> implements TaskLogService {

    @Autowired
    private TaskLogMapper taskLogMapper;

    @Override
    public Page<TaskLog> listTaskLog(TaskLog taskLog, Page<TaskLog> page) {
        QueryWrapper<TaskLog> queryWrapper = new QueryWrapper<>();

        IPage<TaskLog> iPage = taskLogMapper.selectPage(page, queryWrapper);

        return (Page<TaskLog>) iPage;
    }

    @Override
    public TaskLog getById(String id) {
        return taskLogMapper.selectById(id);
    }

    @Override
    public boolean insertTaskLog(TaskLog taskLog) {
        taskLog.setPlanLogId(UUIDUtils.getUUID32());
        return save(taskLog);
    }

    @Override
    public boolean updateTaskLog(TaskLog taskLog) {
        return saveOrUpdate(taskLog);
    }

    @Override
    public boolean deleteTaskLog(String id) {
        return taskLogMapper.deleteById(id) > 0 ? true : false;
    }
}
