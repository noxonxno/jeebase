package com.jeebase.system.controlSys.taskManage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.system.controlSys.taskManage.entity.CutTaskEntity;
import com.jeebase.system.controlSys.taskManage.mapper.ICutTaskMapper;
import com.jeebase.system.controlSys.taskManage.service.ICutTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CutTaskServiceImpl extends ServiceImpl<ICutTaskMapper, CutTaskEntity> implements ICutTaskService {

    @Autowired
    private ICutTaskMapper cutTaskMapper;

    @Override
    public Page<CutTaskEntity> selectList(Page<CutTaskEntity> page, CutTaskEntity cutTaskEntity) {
        LambdaQueryWrapper<CutTaskEntity> lambda = new QueryWrapper<CutTaskEntity>().lambda();
        IPage<CutTaskEntity> cutTaskEntityIPage = cutTaskMapper.selectPage(page, lambda);
        return  (Page<CutTaskEntity>)cutTaskEntityIPage;
    }
}
