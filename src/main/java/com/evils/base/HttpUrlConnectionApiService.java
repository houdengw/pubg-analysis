package com.evils.base;

import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Map;


/**
 * Title: evils
 * CreateDate:  2018/6/19
 *
 * @author houdengw
 * @version 1.0
 */
public class HttpUrlConnectionApiService {

    /**
     * POST请求
     */
    public static ApiResponse doPost(String url, JSONObject params) {


        OutputStream outputStream = null;
        DataOutputStream dataOutputStream = null;
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        String result = null;
        try {
            URLConnection urlConnection = new URL(url).openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) urlConnection;
            // 设置是否向httpUrlConnection输出，post请求，参数要放在http正文内，因此需要设为true,
            // 默认情况下是false;
            httpUrlConnection.setDoOutput(true);
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            httpUrlConnection.setDoInput(true);
            // 忽略缓存
            httpUrlConnection.setUseCaches(false);
            // 设定请求的方法为"POST"，默认是GET
            httpUrlConnection.setRequestMethod("POST");
            //TODO 需作为参数传入 不能在通用方法里写死 设置PUBG所需header
            httpUrlConnection.setRequestProperty("Authorization","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJmZDA1NGUxMC0zNjU3LTAxMzYtMDVlNy03ZGM0MmNhOWYyNjgiLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNTI1OTQwMDI3LCJwdWIiOiJibHVlaG9sZSIsInRpdGxlIjoicHViZyIsImFwcCI6Im15cHViZy1iNzdmOGE3Zi01NTUxLTQ1NDktYTNlYi05Yjk3MTE5OGM5ZWIiLCJzY29wZSI6ImNvbW11bml0eSIsImxpbWl0IjoxMH0.JuPjAlo1UH-UWxZVmufmXsP9tnCrWTdbesJ9oHBhQC8");
            httpUrlConnection.setRequestProperty("Accept", "application/vnd.api+json");

            httpUrlConnection.connect();

            // 建立输入流，向指向的URL传入参数

            String queryString = "";

            if (params != null) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    queryString += entry.getKey()
                            + "="
                            + URLEncoder.encode(entry.getValue().toString(),
                            "UTF-8") + "&";
                }
            }

            if (queryString.length() > 0) {
                queryString = queryString
                        .substring(0, queryString.length() - 1);
                outputStream = httpUrlConnection.getOutputStream();
                dataOutputStream = new DataOutputStream(outputStream);
                dataOutputStream.writeBytes(queryString);

                dataOutputStream.flush();
                outputStream.flush();
            }

            // 获得响应状态
            int responseCode = httpUrlConnection.getResponseCode();

            if (HttpURLConnection.HTTP_OK == responseCode) {
                result = transferToJson(byteArrayOutputStream, httpUrlConnection, inputStream);
                return ApiResponse.ofSuccess(result);
            } else {
                return ApiResponse.ofMessage(responseCode, "请求失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (dataOutputStream != null) {
                try {
                    dataOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return ApiResponse.ofSuccess(null);
    }

    /**
     * GET请求
     */
    public static ApiResponse doGet(String url, JSONObject params) {


        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        String result = null;

        try {
            // URL传入参数
            String queryString = "";

            if (params != null) {
                for (Map.Entry<String, Object> entry : params.entrySet()) {
                    queryString += entry.getKey()
                            + "="
                            + URLEncoder.encode(entry.getValue().toString(),
                            "UTF-8") + "&";
                }
            }

            if (queryString.length() > 0) {
                queryString = queryString
                        .substring(0, queryString.length() - 1);

                url = url + "?" + queryString;
            }

            URLConnection urlConnection = new URL(url).openConnection();
            HttpURLConnection httpUrlConnection = (HttpURLConnection) urlConnection;
            // 设置是否向httpUrlConnection输出，post请求，参数要放在http正文内，因此需要设为true,
            // 默认情况下是false;
            httpUrlConnection.setDoOutput(false);
            // 设置是否从httpUrlConnection读入，默认情况下是true;
            httpUrlConnection.setDoInput(true);
            // 忽略缓存
            httpUrlConnection.setUseCaches(false);
            // 设定请求的方法为"POST"，默认是GET
            httpUrlConnection.setRequestMethod("GET");
            //TODO 需作为参数传入 不能在通用方法里写死 设置PUBG所需header
            httpUrlConnection.setRequestProperty("Authorization","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJmZDA1NGUxMC0zNjU3LTAxMzYtMDVlNy03ZGM0MmNhOWYyNjgiLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNTI1OTQwMDI3LCJwdWIiOiJibHVlaG9sZSIsInRpdGxlIjoicHViZyIsImFwcCI6Im15cHViZy1iNzdmOGE3Zi01NTUxLTQ1NDktYTNlYi05Yjk3MTE5OGM5ZWIiLCJzY29wZSI6ImNvbW11bml0eSIsImxpbWl0IjoxMH0.JuPjAlo1UH-UWxZVmufmXsP9tnCrWTdbesJ9oHBhQC8");
            httpUrlConnection.setRequestProperty("Accept", "application/vnd.api+json");

            httpUrlConnection.connect();

            // 获得响应状态
            int responseCode = httpUrlConnection.getResponseCode();

            if (HttpURLConnection.HTTP_OK == responseCode) {
                result = transferToJson(byteArrayOutputStream, httpUrlConnection, inputStream);
                return ApiResponse.ofSuccess(result);
            } else {
                return ApiResponse.ofMessage(responseCode, "请求失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (byteArrayOutputStream != null) {
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return ApiResponse.ofSuccess(null);
    }

    private static String transferToJson(ByteArrayOutputStream byteArrayOutputStream, HttpURLConnection httpUrlConnection, InputStream inputStream) throws IOException {
        byteArrayOutputStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        inputStream = httpUrlConnection.getInputStream();
        while ((len = inputStream.read(buffer)) != -1) {
            byteArrayOutputStream.write(buffer, 0, len);
            byteArrayOutputStream.flush();
        }
        return byteArrayOutputStream.toString("UTF-8");
    }
}
