package com.evils.controller;

import com.evils.base.ApiResponse;
import com.evils.entity.Player;
import com.evils.entity.form.SearchParam;
import com.evils.service.impl.MatchServiceImpl;
import com.evils.service.impl.PlayerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

    @Autowired
    private PlayerServiceImpl playerService;

    @RequestMapping(value="/list",method = RequestMethod.POST)
    public ApiResponse getMatchesByConditons(SearchParam searchParam) {
        String name = searchParam.getName();
        if(name!=null&&!("").equals(name)){
            List<Player> playerList = playerService.findPlayer(name);
            if(playerList.size()==0){
                //初始化用户数据
                Player player = new Player();
                player.setName(name);
                player = playerService.savePlayer(player);
                if(player!=null){
                    //TODO 调用消息队列初始化数据
                }else{
                    return ApiResponse.ofMessage(404,"未找到该玩家");
                }
            }
        }
        return ApiResponse.ofSuccess(matchService.getMatches(searchParam));
    }
}
