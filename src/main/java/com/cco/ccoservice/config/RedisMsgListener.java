package com.cco.ccoservice.config;

import com.cco.ccoservice.common.redis.Receiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

/**
 * @author xiebq
 * @create 2020/3/20 0020
 * @since 1.0.0
 */
@Configuration
public class RedisMsgListener {

    @Autowired
    Receiver receiver;

    /**
     * redis消息队列监听信息
     *
     * @param connectionFactory
     * @param listenerAdapter
     * @return
     */
    @Bean
    RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
                                            MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, new PatternTopic("dealFaBuDingYue"));
        return container;
    }

    /**
     * 监听方法
     *
     * @return
     */
    @Bean(name = "listenerAdapter")
    MessageListenerAdapter listenerAdapter() {
        // 回调数据处理方法
        return new MessageListenerAdapter(receiver, "dealJt");
    }
}
