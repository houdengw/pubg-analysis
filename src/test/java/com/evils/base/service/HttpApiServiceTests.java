package com.evils.base.service;

import com.evils.PubgAnalysisApplicationTests;
import com.evils.base.ApiResponse;
import com.evils.base.HttpApiService;
import com.evils.base.HttpUrlConnectionApiService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Security;

/**
 * Title: evils
 * CreateDate:  2018/5/17
 *
 * @author houdengw
 * @version 1.0
 */
public class HttpApiServiceTests extends PubgAnalysisApplicationTests {

    @Autowired
    private HttpApiService httpApiService;

    @Test
    public void ww() throws Exception {
        String url = "https://www.baidu.com";
        System.out.print("--------------------->"+httpApiService.doGet(url));

    }

    @Test
    public void testPlayer() throws Exception {
        //Security.setProperty("jdk.tls.disabledAlgorithms","SSLv3, DH keySize < 768");
        System.setProperty("javax.net.debug","ssl");
        String apiSecret = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJmZDA1NGUxMC0zNjU3LTAxMzYtMDVlNy03ZGM0MmNhOWYyNjgiLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNTI1OTQwMDI3LCJwdWIiOiJibHVlaG9sZSIsInRpdGxlIjoicHViZyIsImFwcCI6Im15cHViZy1iNzdmOGE3Zi01NTUxLTQ1NDktYTNlYi05Yjk3MTE5OGM5ZWIiLCJzY29wZSI6ImNvbW11bml0eSIsImxpbWl0IjoxMH0.JuPjAlo1UH-UWxZVmufmXsP9tnCrWTdbesJ9oHBhQC8";
        String url = "https://api.playbattlegrounds.com/shards/pc-as/players?filter[playerNames]=hahahouhuhu";
        System.out.println("-------------->"+httpApiService.doGet(url));
    }

    @Test
    public void test() throws IOException {
        URL url = new URL("https://api.playbattlegrounds.com/shards/pc-as/players?filter[playerNames]=hahahouhuhu");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization","Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJmZDA1NGUxMC0zNjU3LTAxMzYtMDVlNy03ZGM0MmNhOWYyNjgiLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNTI1OTQwMDI3LCJwdWIiOiJibHVlaG9sZSIsInRpdGxlIjoicHViZyIsImFwcCI6Im15cHViZy1iNzdmOGE3Zi01NTUxLTQ1NDktYTNlYi05Yjk3MTE5OGM5ZWIiLCJzY29wZSI6ImNvbW11bml0eSIsImxpbWl0IjoxMH0.JuPjAlo1UH-UWxZVmufmXsP9tnCrWTdbesJ9oHBhQC8");
        conn.setRequestProperty("Accept", "application/vnd.api+json");

        //conn.getInputStream();
        if(200 == conn.getResponseCode()){
            //得到输入流
            InputStream is =conn.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while(-1 != (len = is.read(buffer))){
                baos.write(buffer,0,len);
                baos.flush();
            }
            System.out.println(baos.toString("UTF-8"));
        }


    }

    @Test
    public void test2(){
        String url = "https://api.playbattlegrounds.com/shards/pc-as/players?filter[playerNames]=hahahouhuhu";
        ApiResponse apiResponse = HttpUrlConnectionApiService.doGet(url,null);
        System.out.println(apiResponse.getData());

    }


}
