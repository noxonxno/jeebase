package com.jeebase.system.controlSys.reportAction.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.controlSys.reportAction.entity.CutActionEntity;

public interface ICutActionService extends IService<CutActionEntity> {
    Page<CutActionEntity> selectList(Page<CutActionEntity> page, CutActionEntity cutActionEntity);
}
