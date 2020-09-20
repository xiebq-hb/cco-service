package com.cco.ccoservice.common.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author xiebq
 * @create 2020/3/19 0019
 * @since 1.0.0
 */
@Component
@Slf4j
public class MessageProducer extends Thread{

    @Autowired
    private RedisTemplate redisTemplate;

    public static final String MESSAGE_KEY = "message:queue";
    private volatile int count;

    public void putMessage(String message){
        Long size = redisTemplate.opsForList().leftPush(MESSAGE_KEY, message);
        log.info(Thread.currentThread().getName() +  " push message,size=" + size + ",count="+ count);
        count ++;
    }

    @Override
    public synchronized void run() {
        for (int i = 0; i < 5; i++) {
            this.putMessage("message " + count);
        }
    }
}
