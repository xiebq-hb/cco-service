package com.cco.ccoservice.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 请求限流注解
 * @author xiebq
 * @desc
 * @create 2020/3/2 0002
 * @since 1.0.0
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccessLimit {

    /**
     * 请求限制数
     * @return
     */
    int limit();

    /**
     * 时间范围
     * @return
     */
    int timeScope();
}
