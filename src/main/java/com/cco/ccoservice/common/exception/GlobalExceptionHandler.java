package com.cco.ccoservice.common.exception;

import com.cco.ccoservice.common.response.RespResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * 全局异常处理器
 * @author xiebq
 * @create 2020/3/2 0002
 * @since 1.0.0
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    public static final int CODE_SERVER_ERROR     = 500;     //服务器错误
    public static final int CODE_OPER_ERROR       = 1050;    //操作频繁

    /**
     * 通用异常
     * 功能描述: <br>
     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date: 2020/3/2 上午 10:25
     */
    @ExceptionHandler(value = Exception.class)
    public RespResult allExceptionHandel(HttpServletRequest request, Exception e) {
        log.error(e.getMessage(), e);
        return RespResult.error(CODE_SERVER_ERROR, "系统繁忙，请稍后再试");
    }

    /**
     * 接口限流异常
     * 功能描述: <br>
     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date: 2020/3/2 上午 10:25
     */
    @ExceptionHandler(value = RequestLimitException.class)
    public RespResult limitRequestExceptionHandel(HttpServletRequest request, RequestLimitException e) {
        //log.error(e.getMessage(), e);
        return RespResult.error(CODE_OPER_ERROR, "请求过于频繁，请稍后重试");
    }
}
