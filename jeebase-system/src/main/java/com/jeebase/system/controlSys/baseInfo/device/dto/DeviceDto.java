package com.jeebase.system.controlSys.baseInfo.device.dto;

import com.jeebase.system.controlSys.baseInfo.device.entity.DeviceEntitly;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceDto extends DeviceEntitly {
    private Integer PageSize;
    private Integer PageNumber;
}
