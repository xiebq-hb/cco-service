package com.cco.ccoservice.common.kafka;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 消费者
 * @author xiebq
 * @create 2020/3/18 0018
 * @since 1.0.0
 */
@Component
@Slf4j
public class KafkaReceiver {

    @KafkaListener(topics = {"helloKafka"}, groupId = "group_id")
    public void consume(String message) {
        log.info("## consume message: {}", message);
    }
}
