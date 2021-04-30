package com.jeebase.system.controlSys.baseInfo.location.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.common.base.BusinessException;
import com.jeebase.system.controlSys.baseInfo.location.entity.LocationEntitly;
import com.jeebase.system.controlSys.baseInfo.location.mapper.ILocationMapper;
import com.jeebase.system.controlSys.baseInfo.location.service.ILocationService;
import com.jeebase.system.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class LocationServiceImpl extends ServiceImpl<ILocationMapper, LocationEntitly>
        implements ILocationService {
    @Autowired
    private ILocationMapper locationMapper;

    @Override
    public LocationEntitly getLocationById(LocationEntitly locationEntitly) {
        return locationMapper.selectById(locationEntitly.getLocationId());
    }

    @Override
    public Page<LocationEntitly> locationList(Page<LocationEntitly> page, LocationEntitly locationEntitly) {
        QueryWrapper<LocationEntitly> wrapper = new QueryWrapper<LocationEntitly>();
        //查询条件
        wrapper.eq(locationEntitly.getLocationCode() != null && !(("").equals(locationEntitly.getLocationCode())), "location_code", locationEntitly.getLocationCode())
                .eq("location_state","0")
                .like(locationEntitly.getSteelType() != null && !(("").equals(locationEntitly.getSteelType())), "steel_type", locationEntitly.getSteelType());

        Page<LocationEntitly> licationPage = new Page<>(page.getCurrent(), page.getSize());

        IPage<LocationEntitly> locationList = locationMapper.selectPage(licationPage,wrapper);

        return (Page<LocationEntitly>) locationList;
    }

    @Override
    public boolean removeLocation(String locationId) {
        LocationEntitly locationEntitly = locationMapper.selectById(locationId);
        locationEntitly.setLocationState("1");
        return saveOrUpdate(locationEntitly);
    }

    @Override
    public boolean addLocation(LocationEntitly locationEntitly) {
        if(locationEntitly.getLocationTotal()>locationEntitly.getLocationMaxNum()){
            throw new BusinessException("库存数量不能大于最大存放数量");
        }
        locationEntitly.setLocationId(UUIDUtils.getUUID32());
        locationEntitly.setLocationCode(UUIDUtils.getUUID32());
        locationEntitly.setLocationState("0");
        return save(locationEntitly);
    }

    @Override
    public boolean updateLocation(LocationEntitly locationEntitly) {
        LocationEntitly l = locationMapper.selectById(locationEntitly.getLocationId());

        if(locationEntitly.getLocationTotal()>locationEntitly.getLocationMaxNum()){
            throw new BusinessException("库存数量不能大于最大存放数量");
        }

        l.setLocationMaxNum(locationEntitly.getLocationMaxNum());
        l.setSteelType(locationEntitly.getSteelType());
        l.setLocationTotal(locationEntitly.getLocationTotal());

        return saveOrUpdate(l);
    }

    @Override
    public boolean batchRemove(String ids) {
        String[] strs = ids.split(",");
        List idList = Arrays.asList(strs);
        if (idList.isEmpty()) {
            throw new BusinessException("id不能为空");
        }
        List<LocationEntitly> locationList = locationMapper.selectBatchIds(idList);
        for (LocationEntitly la : locationList) {
            la.setLocationState("1");
        }
        return saveOrUpdateBatch(locationList);
    }
}
