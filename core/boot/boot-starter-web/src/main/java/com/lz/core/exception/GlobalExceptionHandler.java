package com.lz.core.exception;


import com.lz.core.service.StatusCode;
import com.lz.core.service.exception.ServiceException;
import com.lz.core.service.response.BaseResponse;
import com.lz.core.service.response.R;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 全局异常处理
 *
 * <p>@RestControllerAdvice=@ControllerAdvice+@ResponseBody</p>
 *
 * @author luyi
 * @date 2020/12/9
 */
@Log4j2
@RestControllerAdvice
public class GlobalExceptionHandler {


    @Value("${spring.application.name:12}")
    private String applicationName;

    /**
     * webMvc参数校验异常: JSR303校验 - @Valid
     *
     * @param exception 异常信息
     * @return 统一的返回结果
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public BaseResponse webMvcValidException(MethodArgumentNotValidException exception) {
        //得到所有的异常信息
        return getBindExceptionBaseResponse(exception.getBindingResult());
    }


    /**
     * @author luyi
     * @date 2020/8/22
     * //@TODO 记录系统不可预期的异常,
     * 处理系统异常
     */
    @ExceptionHandler(value = Throwable.class)
    public BaseResponse exceptionHandler(Exception e) {
        BaseResponse baseResponse;
        if (e instanceof ServiceException) {
            //业务校验抛出的异常
            baseResponse = new BaseResponse(StatusCode.BUS_EXCEPTION.getCode(), e.getMessage(), null);
            // -----------请求参数校验异常------
        } else if (e instanceof BindException) {
            baseResponse = getBindExceptionBaseResponse(((BindException) e).getBindingResult());
        } else {
            log.error(e.getMessage(), e);
            //其他的一些异常，是程序不可控制的
            baseResponse = new BaseResponse(StatusCode.SYSTEM_EXCEPTION.getCode(), StatusCode.SYSTEM_EXCEPTION.getMessage(), null);
            return baseResponse;
        }
        log.error(e.getMessage(), e);
        return baseResponse;
    }

    /**
     * @author luyi
     * @date 2020/8/22
     * 处理系统异常
     */
    @ExceptionHandler(value = IllegalArgumentException.class)
    public BaseResponse exceptionHandler(IllegalArgumentException e) {
        return new BaseResponse(400, e.getMessage(), null);
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public R handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return R.error("数据库中已存在该记录");
    }

//    @ExceptionHandler(AuthorizationException.class)
//    public R handleAuthorizationException(AuthorizationException e){
//        logger.error(e.getMessage(), e);
//        return R.error("没有权限，请联系管理员授权");
//    }

    /**
     * 描述:得到参数校验BindException的返回结果
     *
     * @author luyi
     * @date 2020/12/20
     */
    private BaseResponse getBindExceptionBaseResponse(BindingResult bindingResult) {
        List<FieldError> fieldErrorList = bindingResult.getFieldErrors();
        return new BaseResponse(StatusCode.PARAMS_VALID_EXCEPTION.getCode(), fieldErrorList.get(0).getDefaultMessage());
    }

}
