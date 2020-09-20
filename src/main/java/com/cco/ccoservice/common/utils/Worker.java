package com.cco.ccoservice.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author xiebq
 * @create 2020/3/2 0002
 * @since 1.0.0
 */
@Slf4j
public class Worker {

    /**
     * 模拟批次请求接口
     * @param request
     */
    public List<String> dealTask(List<String> request) {
        StopWatch watch = new StopWatch();
        watch.start();

        List<String> result = new ArrayList<>();
        request.forEach(r ->{
            try {
                //假设每一个请求都花了1s
                Thread.sleep(1000);
                result.add(r);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        watch.stop();
        log.info("花费时间:[{}]s", watch.getTotalTimeMillis());
        return result;
    }

    public static void main(String[] args) {
        Worker worker = new Worker();
        List<String> list = Arrays.asList("第一次请求", "第二次请求", "第三次请求");
        List<String> result = worker.dealTask(list);
        //会使用批量获取的结果值
        log.info("处理批量返回的数据：{}", result);
    }
}
