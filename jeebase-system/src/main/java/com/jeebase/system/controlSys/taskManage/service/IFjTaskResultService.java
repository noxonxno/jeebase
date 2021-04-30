package com.jeebase.system.controlSys.taskManage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.controlSys.taskManage.entity.FjTaskResultEntity;


public interface IFjTaskResultService extends IService<FjTaskResultEntity> {
    Page<FjTaskResultEntity> selectList(Page<FjTaskResultEntity> page, FjTaskResultEntity fjTaskResultEntity);
}
