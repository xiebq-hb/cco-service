package com.cco.ccoservice.common.http.factory;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xiebq
 * @create 2020/3/6 0006
 * @since 1.0.0
 */
public class HttpClientFactory {
    /**
     * @since: 1.0.0
     * @Date: 2020/3/6 下午 15:45
     */
    public static AbstractHttpClient create(String type){
            switch (type){
                case "get":
                    return new HttpclientGet();
                case "postForm":
                    return new HttpClientPostForm();
                case "postBody":
                    return new HttpClientPostBody();
                default:
                    return null;
            }
    }


    public static void main(String[] args) {
        String url = "http://v.juhe.cn/weather/index?format=2&cityname=中山&key=9679a0703822fe67b9cff961ba197b02";
        String url_post = "http://v.juhe.cn/jztk/query";
        // get请求
        String get = HttpClientFactory.create("get")
                .execute(url);
        System.out.println(get);

        System.out.println("====================================================");

        // post请求
        Map<String,String> map = new HashMap<>();
        map.put("subject","4");
        map.put("model","a1");
        map.put("testType","order");
        map.put("key","5d5d6091ba01de0c27e84b4ab2fca5df");

        String postForm = HttpClientFactory.create("postForm").setParams(map).execute(url_post);
        System.out.println(postForm);
    }
}
