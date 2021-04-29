package com.jeebase.system.utils;

import java.util.UUID;

/**
 * 生成uuid
 */
public class UUIDUtils {

    public static String[] getUUID(int num) {

        if (num <= 0) {
            return null;
        }

        String[] uuidArr = new String[num];

        for (int i = 0; i < uuidArr.length; i++) {
            uuidArr[i] = getUUID32();
        }

        return uuidArr;
    }

    //得到32位的uuid
    public static String getUUID32() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }
}
