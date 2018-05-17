package com.evils.base.service;

import com.evils.PubgAnalysisApplicationTests;
import com.evils.base.HttpApiService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
    public void testHttpClient() throws Exception {
        String url = "http://www.baidu.com";
        System.out.print("--------------------->"+httpApiService.doGet(url));

    }
}
