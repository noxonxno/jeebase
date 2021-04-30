package com.jeebase.system.utils;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;

public class JsonUtils {

    public static String toJsonString(Object object) {
        return JSONObject.toJSONString(object);
    }

    public static JSONObject toJSONObject(String objStr) {
        return JSONObject.parseObject(objStr, Feature.OrderedField);
    }

    public static String getString(JSONObject jsonObject, String key) {
        return jsonObject.getString(key);
    }

    public static String jsonWrap(String sign, String response, boolean isEncrypt) {
        JSONObject json = new JSONObject();
        if (isEncrypt) {
            json.put("response", response);
        } else {
            json.put("response", JsonUtils.toJSONObject(response));
        }
        json.put("sign", sign);
        return json.toString();
    }
}
