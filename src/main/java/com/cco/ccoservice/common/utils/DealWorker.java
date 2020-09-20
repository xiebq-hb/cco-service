package com.cco.ccoservice.common.utils;

import com.cco.ccoservice.common.http.HttpClientHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;

/**
 * 线程请求处理类
 * @author xiebq
 * @create 2020/3/2 0002
 * @since 1.0.0
 */
public class DealWorker implements Runnable{
    /** 正在运行的线程数 */
    private CountDownLatch runningTreadNum;
    /**待处理请求*/
    private String request;
    /**待返回结果列表*/
    private List<String> countDownResultList;

    String api_url = "http://localhost:9528/duker-server-api/del/list";

    public DealWorker(CountDownLatch runningTreadNum, String request, List<String> countDownResultList) {
        this.runningTreadNum = runningTreadNum;
        this.request = request;
        this.countDownResultList = countDownResultList;
    }

    @Override
    public void run() {
        Map<String, String> params = new HashMap<>();
        params.clear();
        try {
           params.put("params", this.request);
           HttpClientHelper.doPost(api_url,params);
       }catch (Exception e){

       }finally {
            this.runningTreadNum.countDown();
       }
    }
}
