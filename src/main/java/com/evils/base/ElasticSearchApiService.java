package com.evils.base;

import com.evils.entity.PlayerDetailSingleMatchTemplate;
import com.evils.entity.form.SearchParam;
import com.evils.utils.Constants;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.action.bulk.BulkRequestBuilder;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.sort.SortBuilder;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

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
    private ObjectMapper objectMapper;


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
     * @param playerDetailSingleMatchTemplates
     * @throws IOException
     */
    public void bulkCreateDocuments(ArrayList<PlayerDetailSingleMatchTemplate> playerDetailSingleMatchTemplates) throws IOException {
        BulkRequestBuilder bulkRequest = client.prepareBulk();

        for(PlayerDetailSingleMatchTemplate playerDetailSingleMatchTemplate : playerDetailSingleMatchTemplates){
            bulkRequest.add(client.prepareIndex(Constants.INDEX_NAME, Constants.TYPE_PLAYER_MATCH_NAME, playerDetailSingleMatchTemplate.getId())
                    .setSource(objectMapper.writeValueAsBytes(playerDetailSingleMatchTemplate), XContentType.JSON)
            );

        }


        BulkResponse bulkResponse = bulkRequest.get();
        if (bulkResponse.hasFailures()) {
            logger.error("transfer failed");
        }
    }

    public SearchHits queryDocuments(SearchParam searchParam){

        //处理排序
        SortBuilder sortBuilder = null;
        if(searchParam.getOrderBy()!=null&&!"".equals(searchParam.getOrderBy())){
            sortBuilder = SortBuilders.fieldSort(searchParam.getOrderBy());
            if(searchParam.getOrderType()!=null&&!"".equals(searchParam.getOrderType())){
                sortBuilder.order(SortOrder.fromString(searchParam.getOrderType()));
            }else {
                sortBuilder.order(SortOrder.DESC);
            }
        }

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
        if(searchParam.getName()!=null&&!"".equals(searchParam.getName())){
            boolQuery.filter(QueryBuilders.termQuery("name",searchParam.getName().toLowerCase()));
        }
        if(searchParam.getKills()!=null){
            boolQuery.filter(QueryBuilders.termQuery("kills",searchParam.getKills()));
        }
        if(searchParam.getMatchDate()!=null&&!"".equals(searchParam.getMatchDate())){
            String mDate = searchParam.getMatchDate();
            boolQuery.filter(QueryBuilders.rangeQuery("matchTime").gt(mDate+" 00:00:00").lt(mDate+" 23:59:59"));
        }

        //组装请求参数
        SearchRequestBuilder requestBuilder = client.prepareSearch(Constants.INDEX_NAME)
                .setTypes(Constants.TYPE_PLAYER_MATCH_NAME)
                .setSearchType(SearchType.DFS_QUERY_THEN_FETCH)
                .setQuery(boolQuery)
                .setFrom(0).setSize(10000).setExplain(false);

        if(sortBuilder!=null){
            requestBuilder.addSort(sortBuilder);
        }
        SearchHits searchHits = requestBuilder.get().getHits();

        return searchHits;


    }

}
