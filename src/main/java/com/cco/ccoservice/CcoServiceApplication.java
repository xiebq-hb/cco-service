package com.cco.ccoservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync //配置定时任务注解
@EnableKafka
public class CcoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CcoServiceApplication.class, args);
    }

}
