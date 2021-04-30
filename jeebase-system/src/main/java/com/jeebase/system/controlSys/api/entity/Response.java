package com.jeebase.system.controlSys.api.entity;

import com.jeebase.system.controlSys.api.enums.ResultCode;

public class Response {
    private final static String SUCCESS = "success";

    private final static String FAIL = "fail";

    public static <T> ResponseResult<T> makeOKRsp() {
        return new ResponseResult<T>().setCode(ResultCode.SUCCESS).setMsg(SUCCESS);
    }

    public static <T> ResponseResult<T> makeOKRsp(String message) {
        return new ResponseResult<T>().setCode(ResultCode.SUCCESS).setMsg(message);
    }

    public static <T> ResponseResult<T> makeOKRsp(T data) {
        return new ResponseResult<T>().setCode(ResultCode.SUCCESS).setMsg(SUCCESS).setData(data);
    }

    public static <T> ResponseResult<T> makeErrRsp(String response_msg, T data) {
        return new ResponseResult<T>().setCode(ResultCode.INTERNAL_SERVER_ERROR).setMsg(response_msg).setData(data);
    }

    public static <T> ResponseResult<T> makeRsp(int code, String msg) {
        return new ResponseResult<T>().setCode(code).setMsg(msg);
    }

    public static <T> ResponseResult<T> makeRsp(int code, String msg, T data) {
        return new ResponseResult<T>().setCode(code).setMsg(msg).setData(data);
    }
}
