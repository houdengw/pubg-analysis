package com.evils.base;

import com.evils.entity.PlayerDetailSingleMatchDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

import static org.elasticsearch.common.xcontent.XContentFactory.jsonBuilder;

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

    /**
     * 批量增加数据
     * @param indexName
     * @param typeName
     */
    public void bulkCreateDocuments(String indexName, String typeName, ArrayList<PlayerDetailSingleMatchDTO> playerDetailSingleMatchDTOS) throws IOException {
        BulkRequestBuilder bulkRequest = client.prepareBulk();

        for(PlayerDetailSingleMatchDTO playerDetailSingleMatchDTO:playerDetailSingleMatchDTOS){
            bulkRequest.add(client.prepareIndex(indexName, typeName, UUID.randomUUID().toString())
                    .setSource(jsonBuilder()
                            .startObject()
                            .field("accountid", playerDetailSingleMatchDTO.getAccountId())
                            .field("name", playerDetailSingleMatchDTO.getName())
                            .field("winPlace", playerDetailSingleMatchDTO.getWinPlace())
                            .field("matchId",playerDetailSingleMatchDTO.getMatchId())
                            .field("kills",playerDetailSingleMatchDTO.getKills())
                            .field("killStreaks",playerDetailSingleMatchDTO.getKillStreaks())
                            .field("damageDealt",playerDetailSingleMatchDTO.getDamageDealt())
                            .field("headshotKills",playerDetailSingleMatchDTO.getHeadshotKills())
                            .field("winPointsDelta",playerDetailSingleMatchDTO.getWinPointsDelta())
                            .field("matchTime",playerDetailSingleMatchDTO.getMatchTime())
                            .endObject()
                    )
            );

        }


        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.hasFailures()) {
            logger.error("transfer failed");
        }
    }

}
