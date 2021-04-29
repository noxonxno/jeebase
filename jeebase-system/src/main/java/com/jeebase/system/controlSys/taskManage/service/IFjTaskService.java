package com.jeebase.system.controlSys.taskManage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.controlSys.taskManage.entity.FjTaskEntity;


public interface IFjTaskService extends IService<FjTaskEntity> {

    Page<FjTaskEntity> selectList(Page<FjTaskEntity> page,FjTaskEntity fjTaskEntity);

}
