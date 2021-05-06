package com.jeebase.system.controlSys.reportAction.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.controlSys.reportAction.entity.WmsActionEntity;

public interface IWmsActionService extends IService<WmsActionEntity> {
    Page<WmsActionEntity> selectList(Page<WmsActionEntity> page, WmsActionEntity wmsActionEntity);
}
