package com.evils.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.evils.base.ApiResponse;
import com.evils.base.HttpUrlConnectionApiService;
import com.evils.entity.Player;
import com.evils.repository.PlayerRepository;
import com.evils.service.IPlayerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Title: evils
 * CreateDate:  2018/6/28
 *
 * @author houdengw
 * @version 1.0
 */
@Service
public class PlayerServiceImpl implements IPlayerService{

    @Autowired
    private PlayerRepository playerRepository;

    private Logger logger = LoggerFactory.getLogger(PlayerServiceImpl.class);

    @Override
    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player savePlayer(Player player) {
        String accountid = getAccountId(player.getName());
        if(accountid!=null){
            player.setAccountId(accountid);
            return playerRepository.save(player);
        }
        logger.warn("未找到{}玩家",player.getName());
        return null;
    }

    @Override
    public List<Player> findPlayer(String name) {
        return playerRepository.findByName(name);
    }


    /**
     * 请求pubg API 获取AccountId
     * @param playerName
     * @return
     */
    private String getAccountId(String playerName) {
        String url = "https://api.playbattlegrounds.com/shards/pc-as/players?filter[playerNames]=" + playerName;
        ApiResponse apiResponse = HttpUrlConnectionApiService.doGet(url, null);
        if (apiResponse.getCode() == 200) {
            JSONObject playerJson = JSON.parseObject(apiResponse.getData() + "");
            String accountId = playerJson.getJSONArray("data").getJSONObject(0).getString("id");
            return accountId;
        }
        return null;
    }
}
