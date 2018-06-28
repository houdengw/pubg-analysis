package com.evils.controller;

import com.evils.base.ApiResponse;
import com.evils.service.impl.PlayerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Title: evils
 * CreateDate:  2018/6/28
 *
 * @author houdengw
 * @version 1.0
 */
@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerServiceImpl playerService;

    @ResponseBody
    public ApiResponse getPlayers(){
        return ApiResponse.ofSuccess(playerService.findAllPlayers());
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public ApiResponse initPlayer(){
        //1.本地创建玩家

        //2.初始化玩家数据 保存到ES

        return ApiResponse.ofSuccess(null);
    }

}
