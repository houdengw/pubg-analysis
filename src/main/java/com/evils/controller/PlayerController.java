package com.evils.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.evils.base.ApiResponse;
import com.evils.base.HttpUrlConnectionApiService;
import com.evils.entity.Player;
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
@RequestMapping("/players")
public class PlayerController {

    @Autowired
    private PlayerServiceImpl playerService;

    @ResponseBody
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ApiResponse getPlayers() {
        return ApiResponse.ofSuccess(playerService.findAllPlayers());
    }

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ApiResponse initPlayer(String playerName, String nickName) {
        //1.本地创建玩家
        Player player = new Player("", playerName, nickName);
        player = playerService.savePlayer(player);

        //ToDo 使用消息队列的方式 初始化玩家的AccoundId

        //ToDo 使用消息队列的方式 初始化玩家数据 保存到ES

        return ApiResponse.ofSuccess(player);
    }



}
