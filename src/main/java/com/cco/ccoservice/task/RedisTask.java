package com.cco.ccoservice.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * @author xiebq
 * @create 2020/3/19 0019
 * @since 1.0.0
 */
@Component
@Slf4j
public class RedisTask {

    @Autowired
    private RedisTemplate redisTemplate;
    public static final String MESSAGE_KEY = "message:queue";
    private volatile int count;

    /**默认是fixedDelay 上一次执行完毕时间后执行下一轮*/
    @Scheduled(cron = "0/1 * * * * *")
    public void run() throws InterruptedException {
        redisTemplate.opsForList().rightPop(MESSAGE_KEY,10, TimeUnit.SECONDS);
        log.info(Thread.currentThread().getName() + " consumer message,message=" + " " + ",count=" + count);
        count++;
    }
}
