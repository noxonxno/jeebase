package com.jeebase.system.controlSys.baseInfo.apiInfo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.controlSys.baseInfo.apiInfo.entity.ApiInfoEntitly;

/**
 * @author DELL
 */
public interface IApiService extends IService<ApiInfoEntitly> {

    Page<ApiInfoEntitly> apiList(Page<ApiInfoEntitly> page, ApiInfoEntitly apiInfoEntitly);

    ApiInfoEntitly getApiInfoById(String apiId);

    boolean addApiInfo(ApiInfoEntitly apiInfoEntitly);

    boolean updateApiInfo(ApiInfoEntitly apiInfoEntitly);

    boolean deleteApiInfo(String apiId);

    boolean batchDeleleApiInfo(String apiIds);

    String testApiInfo(ApiInfoEntitly entitly) throws Exception;
}
