package com.jeebase.system.controlSys.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeebase.system.controlSys.api.entity.CutTask;
import com.jeebase.system.controlSys.api.mapper.CutTaskMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class CutAnalysisService {
    @Autowired
    private CutTaskMapper cutTaskMapper;

    public void save(CutTask cutTask) {
        cutTaskMapper.insert(cutTask);
    }

    public List<CutTask> selcetCatfileList() {
        String a = "";
        //TODO
        QueryWrapper<CutTask> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("11", a);
        return cutTaskMapper.selectList(queryWrapper);
    }
}
