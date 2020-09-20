package com.cco.ccoservice.common.utils;

import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * 处理结果的线程
 * @author xiebq
 * @create 2020/3/2 0002
 * @since 1.0.0
 */
public class DealWorker2 implements Runnable{

    //计数器
    private CountDownLatch countDownLatch;
    //处理的请求
    private String detailRequest;
    //返回的结果集
    private List<String> result;

    public DealWorker2(CountDownLatch countDownLatch, String detailRequest, List<String> result) {
        this.countDownLatch = countDownLatch;
        this.detailRequest = detailRequest;
        this.result = result;
    }

    @Override
    public void run() {
        try {
            //模拟耗时1s
            Thread.sleep(1000);
            result.add("处理线程:" + Thread.currentThread().getName() + "," + detailRequest);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //及时进行计数
            countDownLatch.countDown();
        }
    }
}
