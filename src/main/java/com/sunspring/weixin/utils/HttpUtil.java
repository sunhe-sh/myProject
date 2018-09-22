package com.sunspring.weixin.utils;


import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author sunhe
 */
public class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static HttpClient httpClient = HttpClients.createDefault();

    public static JSONObject post(String url, String jsonParam){
        HttpResponse httpResponse = null;
        JSONObject jsonResult = null;
        try {
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Accept","application/json");
            //解决中文乱码问题
            StringEntity stringEntity = new StringEntity(jsonParam.toString(), "utf-8");
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            httpResponse = httpClient.execute(httpPost);
            /**请求发送成功，并得到响应**/
            jsonResult = getJsonObject(url, httpResponse, jsonResult);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    public static JSONObject get(String url){
        HttpResponse httpResponse = null;
        JSONObject jsonResult = null;
        try {
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Accept","application/json");
            httpResponse = httpClient.execute(httpGet);
            /**请求发送成功，并得到响应**/
            jsonResult = getJsonObject(url, httpResponse, jsonResult);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonResult;
    }

    private static JSONObject getJsonObject(String url, HttpResponse httpResponse, JSONObject jsonResult) {
        if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String str = "";
            try {
                /**读取服务器返回过来的json字符串数据**/
                str = EntityUtils.toString(httpResponse.getEntity());
                /**把json字符串转换成json对象**/
                jsonResult = JSONObject.fromObject(str);
            } catch (Exception e) {
                logger.error("请求提交失败:" + url, e);
            }
        }
        return jsonResult;
    }
}
