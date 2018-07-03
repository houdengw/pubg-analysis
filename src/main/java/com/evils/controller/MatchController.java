package com.evils.controller;

import com.evils.base.ApiResponse;
import com.evils.entity.form.SearchParam;
import com.evils.service.impl.MatchServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Title: evils
 * CreateDate:  2018/7/3
 *
 * @author houdengw
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/matches")
public class MatchController {

    @Autowired
    private MatchServiceImpl matchService;

    @RequestMapping(value="/list",method = RequestMethod.POST)
    public ApiResponse getMatchesByConditons(SearchParam searchParam) {
        return ApiResponse.ofSuccess(matchService.getMatches(searchParam));
    }
}
