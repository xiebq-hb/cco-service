package com.cco.ccoservice.common.kafka;

import com.alibaba.fastjson.JSON;
import com.cco.ccoservice.common.kafka.bean.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

/**
 * 消息生产者
 * @author xiebq
 * @create 2020/3/18 0018
 * @since 1.0.0
 */
@Component
@Slf4j
public class KfkaProducer {

    @Resource
    private KafkaTemplate<String, String> kafkaTemplate;

    // 发送消息方法
    public void sendMsg(){
        for (int i = 0; i <10; i++) {
            Message message = new Message();
            message.setId(System.currentTimeMillis()+ "");
            message.setMsg(UUID.randomUUID().toString() + "--"+ i);
            message.setSendTime(new Date());

            log.info("发送消息 ========>>  message={}", JSON.toJSONString(message));
            ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("helloKafka", JSON.toJSONString(message));
            // 监听回调
            future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
                @Override
                public void onFailure(Throwable throwable) {
                    log.error("## Send message fail ...",throwable);
                }

                @Override
                public void onSuccess(SendResult<String, String> stringStringSendResult) {
                    log.info("## Send message success ...");
                }
            });
        }
    }
}
