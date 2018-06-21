package com.evils.config;

import com.evils.utils.PubgDataCollectUtil;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContextBuilder;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.net.ssl.SSLContext;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

/**
 * Title: evils
 * CreateDate:  2018/5/17
 *
 * @author houdengw
 * @version 1.0
 */
@Configuration
public class HttpClientConfig {
    @Value("${http.maxTotal}")
    private Integer maxTotal;

    @Value("${http.defaultMaxPerRoute}")
    private Integer defaultMaxPerRoute;

    @Value("${http.connectTimeout}")
    private Integer connectTimeout;

    @Value("${http.connectionRequestTimeout}")
    private Integer connectionRequestTimeout;

    @Value("${http.socketTimeout}")
    private Integer socketTimeout;

    @Value("${http.staleConnectionCheckEnabled}")
    private boolean staleConnectionCheckEnabled;

    @Bean(name = "httpClientConnectionManager")
    public PoolingHttpClientConnectionManager getHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager httpClientConnectionManager = new PoolingHttpClientConnectionManager();
        httpClientConnectionManager.setMaxTotal(maxTotal);
        httpClientConnectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
        return httpClientConnectionManager;
    }

    @Bean(name = "httpClientBuilder")
    public HttpClientBuilder getHttpClientBuilder(@Qualifier("httpClientConnectionManager") PoolingHttpClientConnectionManager httpClientConnectionManager) {

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();

        httpClientBuilder.setConnectionManager(httpClientConnectionManager);

        return httpClientBuilder;
    }

//    @Bean(name = "sslSocketFactory")
//    public SSLConnectionSocketFactory sslSocketFactory() {
//        SSLContext sslContext = null;
//        try {
//            sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
//                //信任所有
//                @Override
//                public boolean isTrusted(X509Certificate[] xcs, String string) {
//                    return true;
//                }
//            }).build();
//            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
//            return sslsf;
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (KeyManagementException e) {
//            e.printStackTrace();
//        } catch (KeyStoreException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }


    @Bean
    public CloseableHttpClient getCloseableHttpClient(@Qualifier("httpClientBuilder") HttpClientBuilder
                                                              httpClientBuilder) {
        return httpClientBuilder.build();
    }
//    @Bean
//    public CloseableHttpClient getCloseableHttpClient(@Qualifier("sslSocketFactory") SSLConnectionSocketFactory
//                                                              sslSocketFactory) {
//        return HttpClients.custom().setSSLSocketFactory(sslSocketFactory).build();
//    }


    @Bean(name = "builder")
    public RequestConfig.Builder getBuilder() {
        RequestConfig.Builder builder = RequestConfig.custom();
        return builder.setConnectTimeout(connectTimeout)
                .setConnectionRequestTimeout(connectionRequestTimeout)
                .setSocketTimeout(socketTimeout)
                .setStaleConnectionCheckEnabled(staleConnectionCheckEnabled);
    }

    @Bean
    public RequestConfig getRequestConfig(@Qualifier("builder") RequestConfig.Builder builder) {
        return builder.build();
    }

    @Bean
    public PubgDataCollectUtil testConfig(){
        return new PubgDataCollectUtil();
    }



}
