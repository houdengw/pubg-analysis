package com.evils.base.service;

import com.evils.PubgAnalysisApplicationTests;
import com.evils.base.ElasticSearchApiService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * Title: evils
 * CreateDate:  2018/5/18
 *
 * @author houdengw
 * @version 1.0
 */
public class ElasticSearchApiServiceTests extends PubgAnalysisApplicationTests {

    @Autowired
    private ElasticSearchApiService elasticSearchApiService;

    @Test
    public void testCreateDocument(){
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("id","1");
        map.put("title","标题");
        map.put("content","内容");
        elasticSearchApiService.createDocument("testindex","testtype",map);
    }
}
