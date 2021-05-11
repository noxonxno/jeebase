package com.jeebase.system.controlSys.reportAction.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.controlSys.reportAction.entity.RollerActionEntity;
import com.jeebase.system.controlSys.reportAction.entity.RollerActionEntity;

public interface IRollerActionService extends IService<RollerActionEntity> {
    Page<RollerActionEntity> selectList(Page<RollerActionEntity> page, RollerActionEntity RollerActionEntity);
}
