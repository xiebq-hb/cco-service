package com.cco.ccoservice.common.kafka.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author xiebq
 * @create 2020/3/18 0018
 * @since 1.0.0
 */
@Data
public class Message {
    private String id;
    private String msg;
    private Date sendTime;
}
