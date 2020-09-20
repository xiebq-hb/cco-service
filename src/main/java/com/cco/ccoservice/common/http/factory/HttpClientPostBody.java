package com.cco.ccoservice.common.http.factory;

import org.apache.http.HttpEntity;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @author xiebq
 * @create 2020/3/6 0006
 * @since 1.0.0
 */
public class HttpClientPostBody extends AbstractHttpClient{

    private String body;

    /**
     * HTTPCLIENT-POST请求:APPLICATION_JSON类型数据
     * @since: 1.0.0
     * @Date: 2020/3/6 下午 15:35
     */
    @Override
    String execute(String url) {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;
        try {
            httpClient = HttpClients.createDefault();
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000).build();
            httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            // 入参，传入的参数类型为"APPLICATION_JSON"
            StringEntity stringEntity = new StringEntity(body, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String reponseInformation = EntityUtils.toString(entity);
            return reponseInformation;
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (httpPost != null) {
                    httpPost.releaseConnection();
                }
                if (httpClient != null ) {
                    httpClient.close();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    AbstractHttpClient setParams(Object params) {
        body = params.toString();
        return this;
    }
}
