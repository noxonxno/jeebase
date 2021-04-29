package com.jeebase.system.controlSys.planManage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.controlSys.planManage.entity.MesAdviceEntity;

import java.util.List;

public interface IMesAdviceService extends IService<MesAdviceEntity> {

    boolean saveAndCreate(MesAdviceEntity mesAdviceEntity);

    Page<MesAdviceEntity> selectList(Page<MesAdviceEntity> page, MesAdviceEntity mesAdviceEntity);
}
