package com.jeebase.system.controlSys.baseInfo.apiInfo.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.common.base.BusinessException;
import com.jeebase.system.controlSys.baseInfo.apiInfo.entity.ApiInfoEntitly;
import com.jeebase.system.controlSys.baseInfo.apiInfo.mapper.ApiInfoMapper;
import com.jeebase.system.controlSys.baseInfo.apiInfo.service.IApiService;
import com.jeebase.system.utils.HttpUtils;
import com.jeebase.system.utils.JsonUtils;
import com.jeebase.system.utils.UUIDUtils;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author DELL
 */
@Service
public class ApiInfoServiceImpl extends ServiceImpl<ApiInfoMapper, ApiInfoEntitly>
        implements IApiService {

    @Autowired
    private ApiInfoMapper apiInfoMapper;

    @Override
    public Page<ApiInfoEntitly> apiList(Page<ApiInfoEntitly> page, ApiInfoEntitly apiInfoEntitly) {
        QueryWrapper<ApiInfoEntitly> wrapper = new QueryWrapper();
        wrapper.like(apiInfoEntitly.getApiName() != null &&
                        !(("").equals(apiInfoEntitly.getApiName())),
                "api_name", apiInfoEntitly.getApiName());

        IPage<ApiInfoEntitly> apiList = apiInfoMapper.selectPage(page, wrapper);

        return (Page<ApiInfoEntitly>) apiList;
    }

    @Override
    public ApiInfoEntitly getApiInfoById(String apiId) {
        return apiInfoMapper.selectById(apiId);
    }

    @Override
    public boolean addApiInfo(ApiInfoEntitly apiInfoEntitly) {
        apiInfoEntitly.setApiId(UUIDUtils.getUUID32());
        return save(apiInfoEntitly);
    }

    @Override
    public boolean updateApiInfo(ApiInfoEntitly apiInfoEntitly) {
        return saveOrUpdate(apiInfoEntitly);
    }

    @Override
    public boolean deleteApiInfo(String apiId) {
        return apiInfoMapper.deleteById(apiId) > 0 ? true : false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDeleleApiInfo(String apiIds) {

        String[] ids = apiIds.split(",");
        List idList = Arrays.asList(ids);
        if (idList.isEmpty()) {
            throw new BusinessException("id不能为空");
        }
        int devList = apiInfoMapper.selectBatchIds(idList).size();
        try {
            int deleteList = apiInfoMapper.deleteBatchIds(idList);
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
    public String testApiInfo(ApiInfoEntitly apiInfoEntitly) throws Exception {
        String url = apiInfoEntitly.getApiIp() + apiInfoEntitly.getApiUrl();
        String param = apiInfoEntitly.getApiParam();
        /*防止测试调用接口时 参数为空的情况 设置默认参数*/
        if (param == "" || "".equals(param) || param == null) {
            param = "{param:1}";
        }
        String res = HttpUtils.doPost(url, JsonUtils.toJSONObject(param));

        //System.err.println(res);
        JSONObject json = JsonUtils.toJSONObject(res);
        String code = json.get("code").toString();
        if (code.equals("200")) {
            return "接口调用成功~";
        }
        return json.get("msg").toString();
    }


}
