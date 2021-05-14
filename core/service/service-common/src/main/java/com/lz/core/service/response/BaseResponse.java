package com.lz.core.service.response;


import com.lz.core.service.StatusCode;
import lombok.Data;

import java.io.Serializable;

/**
 * @author ly
 */
@Data
public class BaseResponse implements Serializable {


    /**
     * 返回码
     */
    private Integer code;
    /**
     * 消息
     */
    private String message;
    /**
     * 返回数据
     */
    private Object data;

    public BaseResponse() {

    }

    public BaseResponse(Integer code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * 成功
     */
    public static BaseResponse ok() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(StatusCode.SUCCESS.getCode());
        baseResponse.setMessage(StatusCode.SUCCESS.getMessage());
        return baseResponse;
    }

    /**
     * 成功
     */
    public static BaseResponse ok(Object data) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(StatusCode.SUCCESS.getCode());
        baseResponse.setMessage(StatusCode.SUCCESS.getMessage());
        baseResponse.setData(data);
        return baseResponse;
    }

    /**
     * 失败
     */
    public static BaseResponse fail() {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(StatusCode.BUS_EXCEPTION.getCode());
        baseResponse.setMessage(StatusCode.BUS_EXCEPTION.getMessage());
        return baseResponse;
    }

    /**
     * 失败
     */
    public static BaseResponse fail(Object data) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setCode(StatusCode.BUS_EXCEPTION.getCode());
        baseResponse.setData(data);
        baseResponse.setMessage(StatusCode.BUS_EXCEPTION.getMessage());
        return baseResponse;
    }


}
