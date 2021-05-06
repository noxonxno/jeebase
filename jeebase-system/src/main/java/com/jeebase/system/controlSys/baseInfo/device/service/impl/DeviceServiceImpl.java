package com.jeebase.system.controlSys.baseInfo.device.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.common.base.BusinessException;
import com.jeebase.system.controlSys.baseInfo.device.entity.DeviceEntitly;
import com.jeebase.system.controlSys.baseInfo.device.mapper.IDeviceMapper;
import com.jeebase.system.controlSys.baseInfo.device.service.IDeviceService;
import com.jeebase.system.controlSys.baseInfo.location.entity.LocationEntitly;
import com.jeebase.system.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author DELL
 */
@Service
public class DeviceServiceImpl extends ServiceImpl<IDeviceMapper, DeviceEntitly>
        implements IDeviceService {

    @Autowired
    private IDeviceMapper iDeviceMapper;

    @Override
    public DeviceEntitly getDeviceById(String deviceId) {
        return iDeviceMapper.selectById(deviceId);
    }

    @Override
    public DeviceEntitly getDevice(DeviceEntitly deviceEntitly) {
        QueryWrapper<DeviceEntitly> wrapper = new QueryWrapper<>();
        wrapper.eq(deviceEntitly.getDeviceName() != null
                        && !(("").equals(deviceEntitly.getDeviceName())),
                "device_name", deviceEntitly.getDeviceName())
                .eq(deviceEntitly.getDeviceCode() != null,
                        "device_code", deviceEntitly.getDeviceCode())
                .eq(deviceEntitly.getDeviceId() != null,
                        "device_id", deviceEntitly.getDeviceId())
                .eq(deviceEntitly.getDeviceState() != null,
                        "device_state", deviceEntitly.getDeviceState());

        return iDeviceMapper.selectOne(wrapper);
    }

    @Override
    public Page<DeviceEntitly> deviceList(Page<DeviceEntitly> page, DeviceEntitly deviceEntitly) {
        QueryWrapper<DeviceEntitly> wrapper = new QueryWrapper<>();
        wrapper.like(deviceEntitly.getDeviceName() != null
                        && !(("").equals(deviceEntitly.getDeviceName())),
                "device_name", deviceEntitly.getDeviceName())
                .eq(deviceEntitly.getDeviceCode() != null,
                        "device_code", deviceEntitly.getDeviceCode())
                .eq(deviceEntitly.getDeviceState() != null,
                        "device_state", deviceEntitly.getDeviceState());
        IPage<DeviceEntitly> deviceList = iDeviceMapper.selectPage(page, wrapper);

        return (Page<DeviceEntitly>) deviceList;
    }

    @Override
    public boolean addDevice(DeviceEntitly deviceEntitly) {
        deviceEntitly.setDeviceId(UUIDUtils.getUUID32());
        deviceEntitly.setDeviceState("0");
        return save(deviceEntitly);
    }

    @Override
    public boolean updateDevice(DeviceEntitly deviceEntitly) {
        return saveOrUpdate(deviceEntitly);
    }

    @Override
    public boolean deleteDevice(String deviceId) {
        return iDeviceMapper.deleteById(deviceId) > 0 ? true : false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean bacthDevice(String ids) {

        String[] id = ids.split(",");
        List idList = Arrays.asList(id);
        if (idList.isEmpty()) {
            throw new BusinessException("id不能为空");
        }
        int devList = iDeviceMapper.selectBatchIds(idList).size();
        try {
            int deleteList = iDeviceMapper.deleteBatchIds(idList);
            if (devList == deleteList) {
                return true;
            } else {
                throw new Exception("删除未能成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Map<String, String> getDeviceName() {
        List<DeviceEntitly> list = iDeviceMapper.selectList(new QueryWrapper<>());
        Map<String, String> map = new HashMap<>(list.size());
        list.forEach(item -> {
            map.put(item.getDeviceCode(), item.getDeviceCode());
        });
        return map;
    }
}
