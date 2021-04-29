package com.jeebase.system.controlSys.taskManage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.controlSys.taskManage.entity.CutTaskEntity;

public interface ICutTaskService extends IService<CutTaskEntity> {

    Page<CutTaskEntity> selectList(Page<CutTaskEntity> page, CutTaskEntity cutTaskEntity);
}
