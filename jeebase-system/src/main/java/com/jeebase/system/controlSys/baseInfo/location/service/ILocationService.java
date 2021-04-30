package com.jeebase.system.controlSys.baseInfo.location.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.controlSys.baseInfo.location.dto.LocationDto;
import com.jeebase.system.controlSys.baseInfo.location.entity.LocationEntitly;

public interface ILocationService extends IService<LocationEntitly> {

    LocationEntitly getLocationById(LocationEntitly locationEntitly);

    Page<LocationEntitly> locationList(Page<LocationEntitly> page, LocationEntitly locationEntitly);

    boolean removeLocation(String locationId);

    boolean addLocation(LocationEntitly locationEntitly);

    boolean updateLocation(LocationEntitly locationEntitly);

    boolean batchRemove(String ids);

}
