package com.jeebase.system.controlSys.baseInfo.tary.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.controlSys.baseInfo.location.entity.LocationEntitly;
import com.jeebase.system.controlSys.baseInfo.tary.entity.TaryEntitly;

public interface ITaryService extends IService<TaryEntitly> {

    /**
     *  查询托盘
     * @param tary 托盘对象
     * @return 托盘
     */
    TaryEntitly getTaryEntityById(TaryEntitly tary);

    Page<TaryEntitly> trayList(Page<TaryEntitly> page, TaryEntitly tary);

    boolean removeTary(String taryId);

    boolean addTary(TaryEntitly tary);

    boolean updateTary(TaryEntitly tary);

    boolean batchRemove(String ids);

    boolean batchUpdate(String taryFixNum);
}
