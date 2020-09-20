package com.cco.ccoservice.common.utils;

import com.cco.ccoservice.common.http.HttpClientHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author xiebq
 * @create 2020/3/2 0002
 * @since 1.0.0
 */
public class HandlerList {
    private static List<String> LIST = Arrays.asList("aa","sasa","e32","ere","dwdd");

    public static void main(String[] args) {
        long start_time = System.currentTimeMillis();
        String api_url = "http://localhost:9528/duker-server-api/del/list";

        Map<String, String> params = new HashMap<>();
        for (String s : LIST){
            params.clear();
            params.put("params",s);
            HttpClientHelper.doPost(api_url,params);
        }
        long end_time = System.currentTimeMillis();

        System.out.println("耗时： " + (end_time - start_time) + "ms");
    }
}
