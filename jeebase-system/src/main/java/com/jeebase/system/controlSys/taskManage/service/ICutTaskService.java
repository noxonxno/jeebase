package com.jeebase.system.controlSys.taskManage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.common.base.BusinessException;
import com.jeebase.system.controlSys.taskManage.entity.CutTaskEntity;
import org.springframework.transaction.annotation.Transactional;

public interface ICutTaskService extends IService<CutTaskEntity> {

    Page<CutTaskEntity> selectList(Page<CutTaskEntity> page, CutTaskEntity cutTaskEntity);

    boolean doTask(String cutTaskId, String planCode) throws BusinessException;

    @Transactional
    boolean doTaskCallBack(CutTaskEntity taskEntity);
}
