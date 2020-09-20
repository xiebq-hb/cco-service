package com.cco.ccoservice.business.api;

import com.cco.ccoservice.common.redis.RedisHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiebq
 * @create 2020/3/20 0020
 * @since 1.0.0
 */
@RestController
@RequestMapping(value = "/redis")
public class TestRedisController {

    @Autowired
    RedisHelper redisUtil;

    /**
     * 生产者 通过此方法来往redis的list的尾部插入数据
     */
    @RequestMapping("/shengchangzhe")
    public void shengChangZhe() {
        redisUtil.dealShengChangZhe();
    }

    /**
     * 消费者 ，通过此方法往redis的list的头部获取数据，直到list没有数据 阻塞，等到一有数据又继续获取
     */
    @RequestMapping("/xiaoFeiZhe")
    public void xiaoFeiZhe() {
        redisUtil.dealXiaoFeiZhe();
    }

    /**
     * 发布者，发布信息，监听器一旦接收到监听，就进行操作
     */
    @RequestMapping("/faBuDingYue")
    public void faBuDingYue() {
        redisUtil.dealFaBuDingYue();
    }
}
