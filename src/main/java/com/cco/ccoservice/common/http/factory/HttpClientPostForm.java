package com.cco.ccoservice.common.http.factory;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author xiebq
 * @create 2020/3/6 0006
 * @since 1.0.0
 */
public class HttpClientPostForm extends AbstractHttpClient{

    private Map<String, String> param;

    /**
     * HTTPCLIENT-POST请求:x-www-form-urlencoded  类型
     * @since: 1.0.0
     * @Date: 2020/3/6 下午 15:23
     */
    @Override
    String execute(String url) {
        CloseableHttpClient httpClient = null;
        HttpPost httpPost = null;

        try {
            httpClient = HttpClients.createDefault();
            //请求配置
            RequestConfig requestConfig = RequestConfig
                    .custom()
                    .setSocketTimeout(20000)
                    .setConnectTimeout(20000)
                    .build();
            // 创建Httpost实例
            httpPost = new HttpPost(url);
            httpPost.setConfig(requestConfig);
            List<NameValuePair> ps = new ArrayList<>();
            for (Map.Entry e : param.entrySet()){
                NameValuePair nv = new BasicNameValuePair(e.getKey().toString(),e.getValue().toString());
                ps.add(nv);
            }

            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(ps);
            httpPost.setEntity(urlEncodedFormEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            HttpEntity entity = response.getEntity();
            String responseInformation = EntityUtils.toString(entity);
            return responseInformation;
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                if (httpPost != null){
                    httpPost.releaseConnection();
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
        param = (Map<String, String>)params;
        return this;
    }
}
