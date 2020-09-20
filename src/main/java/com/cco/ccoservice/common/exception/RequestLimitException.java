package com.cco.ccoservice.common.exception;

/**
 * 请求限流异常
 * @author xiebq
 * @create 2020/3/2
 * @since 1.0.0
 */
public class RequestLimitException extends RuntimeException{
    public RequestLimitException() {
    }

    public RequestLimitException(String message) {
        super(message);
    }

    public RequestLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestLimitException(Throwable cause) {
        super(cause);
    }

    public RequestLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
