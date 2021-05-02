package com.jeebase.system.utils;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    private static final String DEFAULT_CHARSET = "UTF-8";

    /**
     * get请求
     */
    public static String doGet(String url) {
        //创建CloseableHttpClient对象
        CloseableHttpClient httpclient = HttpClients.createDefault();

        //创建get请求
        HttpGet request = new HttpGet(url);

        // 设置超时时间、请求时间、socket时间都为5秒，允许重定向
        RequestConfig requestConfig = RequestConfig.custom()
                .setConnectTimeout(5000)
                .setConnectionRequestTimeout(5000)
                .setSocketTimeout(5000)
                .setRedirectsEnabled(true)
                .build();
        request.setConfig(requestConfig);

        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(request);

            // 请求发送成功，并得到响应
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                // 读取服务器返回过来的json字符串数据
                return EntityUtils.toString(response.getEntity());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭输入流
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * post请求(用于key-value格式的参数)
     * @param url 请求地址
     * @param params 请求参数map
     */
    public static String doPost(String url, Map params) {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 创建httpPost
        HttpPost httpPost = new HttpPost(url);

        //设置参数
        List<NameValuePair> nvps = new ArrayList<NameValuePair>();
        for (Object key : params.keySet()) {
            String name = (String) key;
            String value = String.valueOf(params.get(name));
            nvps.add(new BasicNameValuePair(name, value));
        }

        CloseableHttpResponse response = null;
        try {
            httpPost.setEntity(new UrlEncodedFormEntity(nvps, DEFAULT_CHARSET));
            response = httpclient.execute(httpPost);

            int status = response.getStatusLine().getStatusCode();
            if (status == HttpStatus.SC_OK) {    //请求成功
                // 读取服务器返回过来的json字符串数据
                return EntityUtils.toString(response.getEntity());
            } else {
                System.out.println("状态码：" + status);
                return EntityUtils.toString(response.getEntity());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static String doPost(String url, String param) {
        CloseableHttpClient httpclient = HttpClients.createDefault();

        // 创建httpPost
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-Type", "application/json");

        StringEntity entity = new StringEntity(param, DEFAULT_CHARSET);
        httpPost.setEntity(entity);

        CloseableHttpResponse response = null;

        try {
            response = httpclient.execute(httpPost);
            StatusLine status = response.getStatusLine();
            int state = status.getStatusCode();
            if (state == HttpStatus.SC_OK) {
                // 读取服务器返回过来的json字符串数据
                return EntityUtils.toString(response.getEntity());
            } else {
                System.out.println("请求返回:" + state + "(" + url + ")");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (httpclient != null) {
                try {
                    httpclient.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

}
