package com.evils.schedule;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.evils.base.ApiResponse;
import com.evils.base.ElasticSearchApiService;
import com.evils.base.HttpApiService;
import com.evils.base.HttpUrlConnectionApiService;
import com.evils.entity.Player;
import com.evils.entity.dto.PlayerDetailSingleMatchDTO;
import com.evils.service.impl.PlayerServiceImpl;
import com.evils.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Title: evils
 * CreateDate:  2018/7/2
 *
 * @author houdengw
 * @version 1.0
 */
@Component
public class QuartzService {

    @Autowired
    private HttpApiService httpApiService;

    @Autowired
    private ElasticSearchApiService elasticSearchApiService;

    @Autowired
    private PlayerServiceImpl playerService;

    private Logger logger = LoggerFactory.getLogger(QuartzService.class);

    /**
     * 定时处理库中所有玩家的比赛数据到ES库中
     * @throws IOException
     */
    @Scheduled(cron = "0 0/3 * * * ?")
    private void initExistedUsersMatches() throws IOException {
        List<Player> playerList = playerService.findAllPlayers();
        logger.debug("开始更新玩家数据...");
        long startTime = System.currentTimeMillis();
        for(Player player:playerList){
            transferDataToES(player.getName());
        }
        long endTime = System.currentTimeMillis();
        logger.debug("结束更新玩家数据...共耗时:"+(endTime - startTime) + "ms");
    }


    /**
     * 获取pubg接口数据并传输到数据到ES
     * @param playerName
     * @throws IOException
     */
    public void transferDataToES(String playerName) throws IOException {
        ArrayList<String> matchList = new ArrayList<>();
        ArrayList<PlayerDetailSingleMatchDTO> matchesByPlayerList = new ArrayList<PlayerDetailSingleMatchDTO>();

        //1.请求API获取玩家最近的比赛
        String url = Constants.PUBG_PLAYER_API + playerName;
        long startTime = System.currentTimeMillis();
        ApiResponse apiResponse = HttpUrlConnectionApiService.doGet(url, null);
        long endTime = System.currentTimeMillis();
        logger.debug("请求玩家"+playerName+"数据消耗时间： " + (endTime - startTime) + "ms");
        JSONObject jsonData = JSON.parseObject(apiResponse.getData() + "");
        JSONArray matches = jsonData.getJSONArray("data").getJSONObject(0).getJSONObject("relationships").getJSONObject("matches").getJSONArray("data");

        //2.遍历所有比赛获取玩家在这场比赛中的数据,并组装数据
        for (Iterator matchIterator = matches.iterator(); matchIterator.hasNext();) {
            PlayerDetailSingleMatchDTO playerDetailSingleMatchDTO = new PlayerDetailSingleMatchDTO();
            JSONObject match = (JSONObject) matchIterator.next();
            String matchId = match.getString("id");
            playerDetailSingleMatchDTO.setMatchId(matchId);
            String matchUrl = Constants.PUBG_MATCH_API + matchId;
            ApiResponse matchApiResponse = HttpUrlConnectionApiService.doGet(matchUrl, null);
            JSONObject matchDetail = JSON.parseObject(matchApiResponse.getData() + "");
            String matchTime = matchDetail.getJSONObject("data").getJSONObject("attributes").getString("createdAt");
            //将API返回的UTC时间转为本地时间
            playerDetailSingleMatchDTO.setMatchTime(utc2Local(matchTime, "yyyy-MM-dd'T'HH:mm:ss'Z'", "yyyy-MM-dd HH:mm:ss"));
            JSONArray players = matchDetail.getJSONArray("included");


            //遍历比赛中的所有玩家获取当前玩家的数据
            for (Iterator playerIterator = players.iterator(); playerIterator.hasNext(); ) {
                JSONObject player = (JSONObject) playerIterator.next();
                if ("participant".equals(player.getString("type"))) {
                    if (playerName.equals(player.getJSONObject("attributes").getJSONObject("stats").getString("name"))) {
                        JSONObject stats = player.getJSONObject("attributes").getJSONObject("stats");
                        playerDetailSingleMatchDTO.setName(stats.getString("name"));
                        playerDetailSingleMatchDTO.setDamageDealt(stats.getString("damageDealt"));
                        playerDetailSingleMatchDTO.setHeadshotKills(Integer.parseInt(stats.getString("headshotKills")));
                        playerDetailSingleMatchDTO.setKills(Integer.parseInt(stats.getString("kills")));
                        playerDetailSingleMatchDTO.setKillStreaks(Integer.parseInt(stats.getString("killStreaks")));
                        playerDetailSingleMatchDTO.setAccountId(stats.getString("playerId"));
                        playerDetailSingleMatchDTO.setWinPlace(Integer.parseInt(stats.getString("winPlace")));

                    }
                }
            }
            matchesByPlayerList.add(playerDetailSingleMatchDTO);
        }

        //3.将组装好的比赛数据传输到ES
        if(matchesByPlayerList.size()>0){
            elasticSearchApiService.bulkCreateDocuments(matchesByPlayerList);
        }



    }

    /**
     * UTC to local
     *
     * @param utcTime
     * @param utcTimePatten
     * @param localTimePatten
     * @return
     */
    private String utc2Local(String utcTime, String utcTimePatten, String localTimePatten) {
        SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePatten);
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));//时区定义并进行时间获取
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
            return utcTime;
        }
        SimpleDateFormat localFormater = new SimpleDateFormat(localTimePatten);
        localFormater.setTimeZone(TimeZone.getDefault());
        String localTime = localFormater.format(gpsUTCDate.getTime());
        return localTime;
    }

}
