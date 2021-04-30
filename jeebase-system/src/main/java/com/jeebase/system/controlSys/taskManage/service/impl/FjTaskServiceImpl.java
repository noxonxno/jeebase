package com.jeebase.system.controlSys.taskManage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.system.controlSys.taskManage.entity.FjTaskEntity;
import com.jeebase.system.controlSys.taskManage.mapper.IFjTaskMapper;
import com.jeebase.system.controlSys.taskManage.service.IFjTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FjTaskServiceImpl extends ServiceImpl<IFjTaskMapper, FjTaskEntity> implements IFjTaskService {

    @Autowired
    private IFjTaskMapper fjTaskMapper;

    @Override
    public Page<FjTaskEntity> selectList(Page<FjTaskEntity> page, FjTaskEntity fjTaskEntity) {

        LambdaQueryWrapper<FjTaskEntity> lambda = new QueryWrapper<FjTaskEntity>().lambda();
        IPage<FjTaskEntity> cutTaskEntityIPage = fjTaskMapper.selectPage(page, lambda);
        return  (Page<FjTaskEntity>)cutTaskEntityIPage;
    }
}
