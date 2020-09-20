package com.cco.ccoservice.business.api;

import com.cco.ccoservice.common.annotation.AccessLimit;
import com.cco.ccoservice.common.kafka.KfkaProducer;
import com.cco.ccoservice.common.queue.MessageConsumer;
import com.cco.ccoservice.common.queue.MessageConsumer2;
import com.cco.ccoservice.common.queue.MessageProducer;
import com.cco.ccoservice.common.response.RespResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author xiebq
 * @create 2020/3/2 0002
 * @since 1.0.0
 */
@Slf4j
@RestController
public class TestLimitController {

    @Autowired
    private MessageProducer messageProducer;


    @GetMapping("/path")
    @AccessLimit(limit = 5, timeScope = 10) // 限制5秒内只能请求5次
    public RespResult getMiaoshaPath(HttpServletRequest request) {
        log.info(" service api [ /path ]");
        return new RespResult().success();
    };

    @Autowired
    private KfkaProducer producer;

    @RequestMapping("/testSendMsg")
    @ResponseBody
    public String testSendMsg(){
        producer.sendMsg();
        return "success";
    }

    @RequestMapping("/testRedisQueue")
    @ResponseBody
    public String testRedisQueue(){
        Thread t1 = new Thread(messageProducer, "thread1");
        Thread t2 = new Thread(messageProducer, "thread2");
        Thread t3 = new Thread(messageProducer, "thread3");
        Thread t4 = new Thread(messageProducer, "thread4");
        Thread t5 = new Thread(messageProducer, "thread5");

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        return "success";
    }

    @Autowired
    private MessageConsumer messageConsumer;
    @Autowired
    private MessageConsumer2 messageConsumer2;

    @RequestMapping("/testRedisQueue-consumer")
    @ResponseBody
    public String testRedisQueueConsumer(){
        Thread t1 = new Thread(messageConsumer, "thread1");
        Thread t2 = new Thread(messageConsumer, "thread2");

        t1.start();
        t2.start();

        return "success";
    }

    @RequestMapping("/testRedisQueue-consumer2")
    @ResponseBody
    public String testRedisQueueConsumer2(){
        Thread t1 = new Thread(messageConsumer2, "thread1");
        Thread t2 = new Thread(messageConsumer2, "thread2");

        t1.start();
        t2.start();
        return "success";
    }
}
