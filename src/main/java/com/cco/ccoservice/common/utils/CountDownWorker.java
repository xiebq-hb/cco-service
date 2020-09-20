package com.cco.ccoservice.common.utils;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 使用CountDown处理多个请求
 * @Author: dingguo
 * @Date: 2019/6/17 晚上8:23
 */
@Slf4j
public class CountDownWorker {

    /**
     * 使用CountDownLatch批处理
     * @param request
     * @return
     */
    private List<String> countDownDealTask(List<String> request) {
        StopWatch watch = new StopWatch();
        watch.start();

        //创建线程安全的返回结果
        List<String> result  = Collections.synchronizedList(Lists.newArrayList());
        if (request != null) {
            //初始化计数器
            CountDownLatch countDownLatch = new CountDownLatch(request.size());
            //遍历处理
            request.forEach(s -> {
                try {
                    DealWorker2 worker = new DealWorker2(countDownLatch, s, result);
                    //创建线程
                    new Thread(worker).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            //如果执行的过程中出现异常，则继续执行
            try {
                countDownLatch.await(1, TimeUnit.MINUTES);
            } catch (InterruptedException e) {
                log.info("countDown deal task error[{}]", e.getMessage());
                e.printStackTrace();
            }
        }
        watch.stop();
        log.info("花费时间:[{}]s", watch.getTotalTimeMillis());
        return result;
    }
    public static void main(String[] args) {
        CountDownWorker worker = new CountDownWorker();
        List<String> list = Lists.newArrayList("第一次请求", "第二次请求", "第三次请求");
        List<String> result = worker.countDownDealTask(list);
        //会使用批量获取的结果值
        log.info("处理批量返回的数据：{}", result);
    }
}
