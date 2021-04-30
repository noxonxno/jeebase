package com.jeebase.system.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jeebase.system.security.entity.User;
import lombok.Getter;
import lombok.Setter;
import org.apache.shiro.SecurityUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;

/**
 * 获取当前登录用户信息
 * @author DELL
 */
public class GetUserUtils {
    public static User getUser() {
        String principal = (String) SecurityUtils.getSubject().getPrincipal();
        JSONObject userObj = new JSONObject();
        if (!StringUtils.isEmpty(principal)) {
            userObj = JSON.parseObject(principal);
        }
        return JSON.toJavaObject(userObj,User.class);
    }
}
