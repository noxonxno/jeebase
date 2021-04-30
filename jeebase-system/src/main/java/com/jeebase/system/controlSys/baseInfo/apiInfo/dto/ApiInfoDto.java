package com.jeebase.system.controlSys.baseInfo.apiInfo.dto;

import com.jeebase.system.controlSys.baseInfo.apiInfo.entity.ApiInfoEntitly;
import lombok.Getter;
import lombok.Setter;

/**
 * @author DELL
 */
@Getter
@Setter
public class ApiInfoDto extends ApiInfoEntitly {
    private Integer PageSize;
    private Integer PageNumber;
}
