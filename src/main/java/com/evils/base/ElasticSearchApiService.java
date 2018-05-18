package com.evils.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Title: evils
 * CreateDate:  2018/5/18
 *
 * @author houdengw
 * @version 1.0
 */
@Component
public class ElasticSearchApiService {

    private Logger logger = LoggerFactory.getLogger(ElasticSearchApiService.class);

    ObjectMapper mapper = new ObjectMapper();


    @Autowired
    private TransportClient client;


    /**
     * TODO 预留创建索引方法
     *
     * @param index
     * @param settings
     */
    public void createIndex(String index, Settings settings) {

    }

    /**
     * 使用map创建索引文档
     *
     * @param indexName
     * @param typeName
     * @param map
     */
    public void createDocument(String indexName, String typeName, Map<String, Object> map) {
        IndexResponse response = this.client.prepareIndex(indexName, typeName).setSource(map).get();
        if (response.status().getStatus() != 200) {
            logger.warn("创建索引文档{}失败,原因:{}", indexName, response.toString());
        }
    }

    /**
     * 使用byte[]创建索引文档
     *
     * @param indexName
     * @param typeName
     * @param json
     */
    public void createDocument(String indexName, String typeName, byte[] json) throws JsonProcessingException {
        IndexResponse response = this.client.prepareIndex(indexName, typeName).setSource(json).get();
        if (response.status().getStatus() != 200) {
            logger.warn("创建索引文档{}失败,原因:{}", indexName, response.toString());
        }
    }

}
