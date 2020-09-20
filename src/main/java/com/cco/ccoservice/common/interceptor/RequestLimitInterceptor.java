package com.cco.ccoservice.common.interceptor;

import com.cco.ccoservice.common.annotation.AccessLimit;
import com.cco.ccoservice.common.exception.RequestLimitException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


/**
 * 请求限流拦截器
 * @author xiebq
 * @create 2020/3/2 0002
 * @since 1.0.0
 */
@Slf4j
public class RequestLimitInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("request prehandle for limit");
        if (handler instanceof HandlerMethod){
            HandlerMethod hm = (HandlerMethod)handler;
            //方法上的限流注解
            AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
            if (Objects.isNull(accessLimit)){
                return true;
            }

            int limit = accessLimit.limit();
            int timeScope = accessLimit.timeScope();
            String key = request.getRequestURI();
            int count = this.redisGet(key);
            log.info(" cuurent limit is " + limit);
            log.info(" cuurent redis count is " + count);
            if (0 == count){
                this.redisSet(key, 1, timeScope);
                return true;
            }else if (count < limit){
                this.redisSet(key, count + 1, timeScope);
                return true;
            }else if (count >= limit){
                throw new RequestLimitException("服务器繁忙，请稍后再试");
            }
        }
        return true;
    }

    private int redisGet(String key) {
        log.info("key " + key);
        Integer count = null;
        try {
            count = (Integer) redisTemplate.opsForValue().get(key);
            if (null == count){
                return 0;
            }
        }catch (Exception e){
            log.error("redis get count error",e);
            return 0;
        }

        return count.intValue();
    }

    private void redisSet(String key, int count, int timeScope) {
        try {
            //存进缓存
            redisTemplate.opsForValue().set(key, count, timeScope, TimeUnit.SECONDS);
        }catch (Exception e){
            log.error("redis set count error",e);
        }
    }
}
