package com.cco.ccoservice.common.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisCluster;

import java.util.List;

/**
 * @author xiebq
 * @create 2020/3/20 0020
 * @since 1.0.0
 */
@Component
public class RedisHelper {
    @Autowired
    JedisCluster jedisCluster;

    @Autowired
    private RedisService redisService;

    /**
     * 生产者
     */
    public void dealShengChangZhe() {
        for (int i = 0; i < 10; i++) {
            jedisCluster.rpush("ceshi", "value1_" + i);
        }
    }

    /**
     * 消费者
     */
    public void dealXiaoFeiZhe() {
        while (true) {
            //阻塞式brpop，List中无数据时阻塞，参数0表示一直阻塞下去，直到List出现数据
            List<String> listingList = jedisCluster.blpop(0, "ceshi");
            System.out.println("线程取数据：{}" + listingList.get(1));
        }
    }

    /**
     * 发布订阅模式
     */
    public void dealFaBuDingYue() {
        redisService.convertAndSend("dealFaBuDingYue", "我是来发布信息的");
    }
}
