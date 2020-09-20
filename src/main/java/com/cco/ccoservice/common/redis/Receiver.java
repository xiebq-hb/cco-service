package com.cco.ccoservice.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author xiebq
 * @create 2020/3/20 0020
 * @since 1.0.0
 */
@Service(value = "receiver")
public class Receiver {
    @Autowired
    private RedisService redisService;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 清除本地缓存
     * @param message
     */
    public  void dealJt(String message){
        System.out.println("我是用来监听信息的");
        System.out.println(message);
    }

    /**
     * 清除本地缓存
     * @param message
     */
    public  void dealJt1(String message){
        System.out.println("我是用来监听信息的1");
        System.out.println(message);
    }
}
