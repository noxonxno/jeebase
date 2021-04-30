package com.jeebase.system.controlSys.taskManage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.system.controlSys.taskManage.entity.FjTaskResultEntity;
import com.jeebase.system.controlSys.taskManage.mapper.IFjTaskResultMapper;
import com.jeebase.system.controlSys.taskManage.service.IFjTaskResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FjTaskResultServiceImpl extends ServiceImpl<IFjTaskResultMapper, FjTaskResultEntity> implements IFjTaskResultService {

    @Autowired
    private IFjTaskResultMapper fjTaskResultMapper;

    @Override
    public Page<FjTaskResultEntity> selectList(Page<FjTaskResultEntity> page, FjTaskResultEntity fjTaskResultEntity) {
        LambdaQueryWrapper<FjTaskResultEntity> lambda = new QueryWrapper<FjTaskResultEntity>().lambda();
        IPage<FjTaskResultEntity> fjTaskResultEntityIPage = fjTaskResultMapper.selectPage(page, lambda);
        return  (Page<FjTaskResultEntity>)fjTaskResultEntityIPage;
    }
}
