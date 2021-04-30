package com.jeebase.system.controlSys.api.enums;

public enum ResultCode {
    // 成功
    SUCCESS(0),

    // 失败
    FAIL(100),

    // 未认证（签名错误）
    UNAUTHORIZED(101),

    //错误102
    ERROR(102),

    //错误103
    ERROR103(103),

    // 接口不存在
    NOT_FOUND(404),

    // 服务器内部错误
    INTERNAL_SERVER_ERROR(500);

    public int code;

    ResultCode(int code) {
        this.code = code;
    }
}
