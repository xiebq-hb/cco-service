package com.cco.ccoservice.config;

import com.cco.ccoservice.common.interceptor.RequestLimitInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器配置
 * @author xiebq
 * @create 2020/3/2 0002
 * @since 1.0.0
 */
@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    /**
     * 初始化拦截器之前进行bean处理
     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date: 2020/3/2 0002 上午 11:16
     */
    @Bean
    public RequestLimitInterceptor requestLimitInterceptor(){
        return new RequestLimitInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 拦截所有请求，通过判断是否有 @LoginRequired 注解 决定是否需要登录
        registry.addInterceptor(requestLimitInterceptor()).addPathPatterns("/**");
        //registry.addInterceptor(AuthorityInterceptor()).addPathPatterns("/**");
    }
}

