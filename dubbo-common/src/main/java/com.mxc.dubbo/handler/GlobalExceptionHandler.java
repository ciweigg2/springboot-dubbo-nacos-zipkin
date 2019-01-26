package com.mxc.dubbo.handler;


import com.mxc.dubbo.common.util.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 类名称：WingExceptionHandler
 * 类描述：异常处理器
 * 创建人：maxiucheng
 * 创建时间：2018年10月26日 10:36
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({RuntimeException.class})
    public Result handlerException(Exception ex) {
        return Result.failure(HttpStatus.INTERNAL_SERVER_ERROR.value(), "服务器打了个小盹儿~请稍候再试");
    }
}
