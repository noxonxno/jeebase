package com.jeebase.system.controlSys.taskManage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.common.base.BusinessException;
import com.jeebase.system.controlSys.taskManage.entity.FjTaskEntity;
import org.springframework.transaction.annotation.Transactional;


public interface IFjTaskService extends IService<FjTaskEntity> {

    Page<FjTaskEntity> selectList(Page<FjTaskEntity> page,FjTaskEntity fjTaskEntity);

    @Transactional
    boolean doTask(String fjTaskId, String planCode) throws BusinessException;

    @Transactional
    boolean doTaskCallBack(FjTaskEntity taskEntity);
}
