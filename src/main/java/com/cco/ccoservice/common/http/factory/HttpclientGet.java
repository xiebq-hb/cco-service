package com.cco.ccoservice.common.http.factory;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author xiebq
 * @create 2020/3/6 0006
 * @since 1.0.0
 */
public class HttpclientGet extends AbstractHttpClient{

    @Override
    String execute(String url) {
        CloseableHttpClient httpClient = null;
        HttpGet httpGet = null;
        try{
            httpClient = HttpClients.createDefault();
            // 请求配置
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
            // 创建httpGet实例
            httpGet = new HttpGet(url);
            // 对HttpGet指定配置
            httpGet.setConfig(requestConfig);
           // 启动httpget请求
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String reponseInformation = EntityUtils.toString(entity, "UTF-8");
            return reponseInformation;
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (httpGet != null){
                    httpGet.releaseConnection();
                }
                if (httpClient != null){
                    httpClient.close();
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    AbstractHttpClient setParams(Object params) {
        return null;
    }
}
