package com.jeebase.system.controlSys.taskManage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.common.base.BusinessException;
import com.jeebase.system.controlSys.taskManage.entity.WmsTaskEntity;
import org.springframework.transaction.annotation.Transactional;

public interface IWmsTaskService extends IService<WmsTaskEntity> {

    Page<WmsTaskEntity> selectList(Page<WmsTaskEntity> page, WmsTaskEntity wmsTaskEntity);

    @Transactional
    boolean doTask(String wmsTaskId, String planCode) throws BusinessException;

    @Transactional
    boolean doTaskCallBack(WmsTaskEntity wmsTaskEntity);
}
