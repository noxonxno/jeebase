package com.jeebase.system.controlSys.taskManage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.controlSys.taskManage.entity.WmsTaskEntity;

public interface IWmsTaskService extends IService<WmsTaskEntity> {

    Page<WmsTaskEntity> selectList(Page<WmsTaskEntity> page, WmsTaskEntity wmsTaskEntity);
}
