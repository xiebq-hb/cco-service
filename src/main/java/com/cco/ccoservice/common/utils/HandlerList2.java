package com.cco.ccoservice.common.utils;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * @author xiebq
 * @create 2020/3/2 0002
 * @since 1.0.0
 */
public class HandlerList2 {
    private static List<String> LIST = Arrays.asList("aa","sasa","e32","ere","dwdd");

    public static void main(String[] args) {
        long start_time = System.currentTimeMillis();
        CountDownLatch runningThreadNum = new CountDownLatch(LIST.size());
        for (String request : LIST){
            DealWorker dealWorker = new DealWorker(runningThreadNum, request, null);
            new Thread(dealWorker).start();
        }
        try {
            //调用CountDownLatch的await方法则当前主线程会等待，直到CountDownLatch类型的runningThreadNum清0
            //每个DealWorker处理完成会对runningThreadNum减1
            //如果等待1分钟后当前主线程都等不到runningThreadNum清0，则认为超时，直接中断，抛出中断异常InterruptedException
            runningThreadNum.await(1, TimeUnit.MINUTES);
        } catch (InterruptedException e) {
            //此处简化处理，非正常中断应该抛出异常或返回错误结果
        }
        long end_time = System.currentTimeMillis();

        System.out.println("耗时： " + (end_time - start_time) + "ms");
    }
}
