package com.cco.ccoservice.common.queue;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 消费者就算队列没有消息也会进行连接消费，这样造成了浪费资源
 * @author xiebq
 * @create 2020/3/19 0019
 * @since 1.0.0
 */
@Component
@Slf4j
public class MessageConsumer2 implements Runnable{

    @Autowired
    private RedisTemplate redisTemplate;

    public static final String MESSAGE_KEY = "message:queue";
    private volatile int count;

    public void consumerMessage() {
        redisTemplate.opsForList().rightPop(MESSAGE_KEY,10, TimeUnit.SECONDS);
        log.info(Thread.currentThread().getName() + " consumer message,message=" + " " + ",count=" + count);
        count++;
    }

    @Override
    public void run() {
//        for (int i = 0; i < 20; i++) {
//            consumerMessage();
//        }

        while (true) {
            consumerMessage();
        }
    }
}
