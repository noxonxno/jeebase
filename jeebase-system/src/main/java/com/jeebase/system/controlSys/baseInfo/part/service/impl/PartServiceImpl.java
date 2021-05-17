package com.jeebase.system.controlSys.baseInfo.part.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.common.base.BusinessException;
import com.jeebase.system.controlSys.baseInfo.part.entity.PartEntitly;
import com.jeebase.system.controlSys.baseInfo.part.mapper.IPartMapper;
import com.jeebase.system.controlSys.baseInfo.part.service.IPartService;
import com.jeebase.system.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PartServiceImpl extends ServiceImpl<IPartMapper, PartEntitly>
        implements IPartService {
    @Autowired
    private IPartMapper partMapper;

    @Override
    public PartEntitly getPartById(PartEntitly partEntitly) {
        return partMapper.selectById(partEntitly.getPartId());
    }

    @Override
    public Page<PartEntitly> partList(Page<PartEntitly> page, PartEntitly partEntitly) {
        QueryWrapper<PartEntitly> wrapper = new QueryWrapper<PartEntitly>();
        //查询条件
        wrapper.eq("fj_type",1);
        wrapper.like(partEntitly.getPartCode()!=null && !(("").equals(partEntitly.getPartCode())),"part_code",partEntitly.getPartCode());
        wrapper.select().orderByDesc("create_time");
        Page<PartEntitly> licationPage = new Page<>(page.getCurrent(), page.getSize());

        IPage<PartEntitly> locationList = partMapper.selectPage(licationPage, wrapper);

        return (Page<PartEntitly>) locationList;
    }

    @Override
    public Page<PartEntitly> pcPartList(Page<PartEntitly> page, PartEntitly partEntitly) {
        QueryWrapper<PartEntitly> wrapper = new QueryWrapper<PartEntitly>();
        //查询条件
        wrapper.like(partEntitly.getPartCode()!=null && !(("").equals(partEntitly.getPartCode())),"part_code",partEntitly.getPartCode());
        wrapper.select().orderByDesc("create_time");
        Page<PartEntitly> licationPage = new Page<>(page.getCurrent(), page.getSize());

        IPage<PartEntitly> locationList = partMapper.selectPage(licationPage, wrapper);

        return (Page<PartEntitly>) locationList;
    }
    @Override
    public boolean removePart(String partId) {
        PartEntitly partEntitly = partMapper.selectById(partId);

        return partMapper.deleteById(partId) >=1 ? true:false;
    }

    @Override
    public boolean addPart(PartEntitly partEntitly) {
        partEntitly.setPartId(UUIDUtils.getUUID32());
        partEntitly.setFjType(1);
        return save(partEntitly);
    }


    @Override
    public boolean pcAddPart(PartEntitly partEntitly) {
        partEntitly.setPartId(UUIDUtils.getUUID32());
        partEntitly.setFjType(0);
        partEntitly.setPartState("0");
        return save(partEntitly);
    }
    @Override
    public boolean updatePart(PartEntitly partEntitly) {
        PartEntitly part = partMapper.selectById(partEntitly.getPartId());


        return saveOrUpdate(partEntitly);
    }
}
