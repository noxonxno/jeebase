package com.jeebase.system.controlSys.taskManage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.system.controlSys.taskManage.entity.WmsTaskEntity;
import com.jeebase.system.controlSys.taskManage.mapper.IWmsTaskMapper;
import com.jeebase.system.controlSys.taskManage.service.IWmsTaskService;
import org.springframework.beans.factory.annotation.Autowired;

public class WmsTaskServiceImpl extends ServiceImpl<IWmsTaskMapper, WmsTaskEntity> implements IWmsTaskService {

    @Autowired
    private IWmsTaskMapper wmsTaskMapper;

    @Override
    public Page<WmsTaskEntity> selectList(Page<WmsTaskEntity> page, WmsTaskEntity wmsTaskEntity) {

        LambdaQueryWrapper<WmsTaskEntity> lambda = new QueryWrapper<WmsTaskEntity>().lambda();

        IPage<WmsTaskEntity> cutTaskEntityIPage = wmsTaskMapper.selectPage(page, lambda);
        return  (Page<WmsTaskEntity>)cutTaskEntityIPage;
    }
}
