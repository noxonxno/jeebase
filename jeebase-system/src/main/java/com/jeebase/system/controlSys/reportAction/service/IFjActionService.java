package com.jeebase.system.controlSys.reportAction.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.controlSys.reportAction.entity.FjActionEntity;

public interface IFjActionService extends IService<FjActionEntity> {
    Page<FjActionEntity> selectList(Page<FjActionEntity> page, FjActionEntity fjActionEntity);
}
