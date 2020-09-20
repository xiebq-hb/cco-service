package com.cco.ccoservice.config;

import com.baomidou.mybatisplus.plugins.OptimisticLockerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author xiebq
 * @create 2020/3/3 0003
 * @since 1.0.0
 */
@EnableTransactionManagement
@Configuration
@MapperScan("com.cco.ccoservice.business.mapper")
public class MybatisPlusConfig {
    /**
     *   乐观锁
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }
}
