package com.jeebase.system.common.component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.jeebase.system.security.entity.User;
import com.jeebase.system.utils.GetUserUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * @ClassName: MyMetaObjectHandler
 * @Description: 自定义填充公共字段
 * @author jeebase-WANGLEI
 * @date 2018年5月19日 上午10:32:29
 */
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {

        User user = GetUserUtils.getUser();

        Object creator = getFieldValByName("creator", metaObject);
        if (null == creator && null != user) {
            setFieldValByName("creator", user.getId(), metaObject);
        }
        Object createTime = getFieldValByName("createTime", metaObject);
        if (null == createTime) {
            setFieldValByName("createTime", LocalDateTime.now(), metaObject);
        }



        if(metaObject.hasSetter("locationUser")){
            setFieldValByName("locationUser", user.getUserName(), metaObject);
        }
        if(metaObject.hasSetter("createUser")){
            setFieldValByName("createUser", user.getUserName(), metaObject);
        }

    }

    @Override
    public void updateFill(MetaObject metaObject) {
        User user = GetUserUtils.getUser();

        Object creator = getFieldValByName("creator", metaObject);
        if (null == creator && null != user) {
            setFieldValByName("creator", user.getId(), metaObject);
        }
        Object updateTime = getFieldValByName("updateTime", metaObject);
        if (null == updateTime) {
            setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }


        /*自己需要新增的建议用一下判断方式*/
        if(metaObject.hasSetter("updateUser")){
            setFieldValByName("updateUser", user.getUserName(), metaObject);
        }
    }
}
