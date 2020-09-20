package com.cco.ccoservice.common.http.factory;

/**
 * @author xiebq
 * @create 2020/3/6 0006
 * @since 1.0.0
 */
public abstract class AbstractHttpClient {

    /**
     * httpclient的三种请求方式执行的基本url方法
     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date: 2020/3/6 下午 15:03
     */
    abstract String execute(String url);

    /**
     * 分别对Get，Post的请求传参，Get没有参数，Post含有参数
     * @return:
     * @since: 1.0.0
     * @Author:
     * @Date: 2020/3/6 下午 15:04
     */
    abstract AbstractHttpClient setParams(Object params);
}
