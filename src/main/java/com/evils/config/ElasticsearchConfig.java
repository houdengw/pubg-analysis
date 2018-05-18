package com.evils.config;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.transport.TransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * Title: evils
 * CreateDate:  2018/5/18
 *
 * @author houdengw
 * @version 1.0
 */
@Configuration
public class ElasticsearchConfig {

    @Value("${elasticsearch.cluster.name}")
    private String clusterName;

    @Value("${elasticsearch.tcp.host}")
    private String host;

    @Value("${elasticsearch.tcp.port}")
    private int port;

    /**
     * 初始化client
     * @return
     * @throws UnknownHostException
     */
    @Bean
    public TransportClient getTransportClient() throws UnknownHostException {
        Settings settings = Settings.builder().put("cluster.name",clusterName).build();
        TransportClient client = new PreBuiltTransportClient(settings).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName(host), port));
        return client;
    }
}
