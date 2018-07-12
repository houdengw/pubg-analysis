package com.evils.base.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.evils.PubgAnalysisApplicationTests;
import com.evils.base.ApiResponse;
import com.evils.base.ElasticSearchApiService;
import com.evils.base.HttpApiService;
import com.evils.base.HttpUrlConnectionApiService;
import com.evils.entity.PlayerDetailSingleMatchTemplate;
import com.evils.entity.form.SearchParam;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.TimeZone;

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

    @Autowired
    private ElasticSearchApiService elasticSearchApiService;

    @Test
    public void ww() throws Exception {
        String url = "https://www.baidu.com";
        System.out.print("--------------------->" + httpApiService.doGet(url));

    }

    @Test
    public void testPlayer() throws Exception {
        //Security.setProperty("jdk.tls.disabledAlgorithms","SSLv3, DH keySize < 768");
        System.setProperty("javax.net.debug", "ssl");
        String apiSecret = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJmZDA1NGUxMC0zNjU3LTAxMzYtMDVlNy03ZGM0MmNhOWYyNjgiLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNTI1OTQwMDI3LCJwdWIiOiJibHVlaG9sZSIsInRpdGxlIjoicHViZyIsImFwcCI6Im15cHViZy1iNzdmOGE3Zi01NTUxLTQ1NDktYTNlYi05Yjk3MTE5OGM5ZWIiLCJzY29wZSI6ImNvbW11bml0eSIsImxpbWl0IjoxMH0.JuPjAlo1UH-UWxZVmufmXsP9tnCrWTdbesJ9oHBhQC8";
        String url = "https://api.playbattlegrounds.com/shards/pc-as/players?filter[playerNames]=hahahouhuhu";
        System.out.println("-------------->" + httpApiService.doGet(url));
    }

    @Test
    public void test() throws IOException {
        URL url = new URL("https://api.playbattlegrounds.com/shards/pc-as/players?filter[playerNames]=hahahouhuhu");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiJmZDA1NGUxMC0zNjU3LTAxMzYtMDVlNy03ZGM0MmNhOWYyNjgiLCJpc3MiOiJnYW1lbG9ja2VyIiwiaWF0IjoxNTI1OTQwMDI3LCJwdWIiOiJibHVlaG9sZSIsInRpdGxlIjoicHViZyIsImFwcCI6Im15cHViZy1iNzdmOGE3Zi01NTUxLTQ1NDktYTNlYi05Yjk3MTE5OGM5ZWIiLCJzY29wZSI6ImNvbW11bml0eSIsImxpbWl0IjoxMH0.JuPjAlo1UH-UWxZVmufmXsP9tnCrWTdbesJ9oHBhQC8");
        conn.setRequestProperty("Accept", "application/vnd.api+json");

        //conn.getInputStream();
        if (200 == conn.getResponseCode()) {
            //得到输入流
            InputStream is = conn.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int len = 0;
            while (-1 != (len = is.read(buffer))) {
                baos.write(buffer, 0, len);
                baos.flush();
            }
            System.out.println(baos.toString("UTF-8"));
        }


    }

    @Test
    public void test2() throws IOException {
        String playerName = "DuiZhangBie_KQ";
        ArrayList<String> matchList = new ArrayList<>();
        ArrayList<PlayerDetailSingleMatchTemplate> matchesByPlayerList = new ArrayList<PlayerDetailSingleMatchTemplate>();
        String url = "https://api.playbattlegrounds.com/shards/pc-as/players?filter[playerNames]="+playerName;
        long startTime=System.currentTimeMillis();
        ApiResponse apiResponse = HttpUrlConnectionApiService.doGet(url, null);
        long endTime=System.currentTimeMillis();
        System.out.println("请求玩家数据消耗时间： "+(endTime-startTime)+"ms");
        JSONObject jsonData = JSON.parseObject(apiResponse.getData()+"");
        JSONArray matches = jsonData.getJSONArray("data").getJSONObject(0).getJSONObject("relationships").getJSONObject("matches").getJSONArray("data");

        long analysiStartTime=System.currentTimeMillis();
        for (int i = 0; i < matches.size(); i++) {
            PlayerDetailSingleMatchTemplate playerDetailSingleMatchTemplate = new PlayerDetailSingleMatchTemplate();
            JSONObject match = matches.getJSONObject(i);
            String matchId = match.getString("id");
            playerDetailSingleMatchTemplate.setMatchId(matchId);
            String matchUrl = "https://api.playbattlegrounds.com/shards/pc-as/matches/" + matchId;
            ApiResponse matchApiResponse = HttpUrlConnectionApiService.doGet(matchUrl, null);
            JSONObject matchDetail = JSON.parseObject(matchApiResponse.getData()+"");
            String matchTime = matchDetail.getJSONObject("data").getJSONObject("attributes").getString("createdAt");
            playerDetailSingleMatchTemplate.setMatchTime(utc2Local(matchTime,"yyyy-MM-dd'T'HH:mm:ss'Z'","yyyy-MM-dd HH:mm:ss"));
            JSONArray players = matchDetail.getJSONArray("included");


            for(Iterator iterator = players.iterator(); iterator.hasNext();){
                JSONObject player = (JSONObject)iterator.next();
                if("participant".equals(player.getString("type"))){
                    if(playerName.equals(player.getJSONObject("attributes").getJSONObject("stats").getString("name"))){
                        JSONObject stats = player.getJSONObject("attributes").getJSONObject("stats");
                        playerDetailSingleMatchTemplate.setPlayerId(stats.getString("playerId"));
                        playerDetailSingleMatchTemplate.setName(stats.getString("name"));
                        playerDetailSingleMatchTemplate.setHeals(stats.getInteger("heals"));
                        playerDetailSingleMatchTemplate.setAssists(stats.getInteger("assists"));
                        playerDetailSingleMatchTemplate.setBoosts(stats.getInteger("boosts"));
                        playerDetailSingleMatchTemplate.setKills(stats.getInteger("kills"));
                        playerDetailSingleMatchTemplate.setKillPlace(stats.getInteger("killPlace"));
                        playerDetailSingleMatchTemplate.setKillPoints(stats.getLong("killPoints"));
                        playerDetailSingleMatchTemplate.setKillPointsDelta(stats.getDouble("killPointsDelta"));
                        playerDetailSingleMatchTemplate.setLongestKill(stats.getDouble("longestKill"));
                        playerDetailSingleMatchTemplate.setKillStreaks(stats.getInteger("killStreaks"));
                        playerDetailSingleMatchTemplate.setRoadKills(stats.getInteger("roadKills"));
                        playerDetailSingleMatchTemplate.setHeadshotKills(stats.getInteger("headshotKills"));
                        playerDetailSingleMatchTemplate.setDamageDealt(stats.getDouble("damageDealt"));
                        playerDetailSingleMatchTemplate.setRideDistance(stats.getDouble("rideDistance"));
                        playerDetailSingleMatchTemplate.setSwimDistance(stats.getDouble("swimDistance"));
                        playerDetailSingleMatchTemplate.setWalkDistance(stats.getDouble("walkDistance"));
                        playerDetailSingleMatchTemplate.setTimeSurvived(stats.getDouble("timeSurvived"));
                        playerDetailSingleMatchTemplate.setVehicleDestroys(stats.getInteger("vehicleDestroys"));
                        playerDetailSingleMatchTemplate.setWeaponsAcquired(stats.getInteger("weaponsAcquired"));
                        playerDetailSingleMatchTemplate.setWinPoints(stats.getLong("winPoints"));
                        playerDetailSingleMatchTemplate.setWinPointsDelta(stats.getDouble("winPointsDelta"));
                        playerDetailSingleMatchTemplate.setWinPlace(Integer.parseInt(stats.getString("winPlace")));

                    }
                }
            }
            matchesByPlayerList.add(playerDetailSingleMatchTemplate);
        }
        long analysiEndTime=System.currentTimeMillis();
        System.out.println("解析比赛json消耗时间： "+(analysiEndTime-analysiStartTime)+"ms");

        for(PlayerDetailSingleMatchTemplate playerDetailSingleMatchTemplate :matchesByPlayerList){
            System.out.println("比赛时间:"+ playerDetailSingleMatchTemplate.getMatchTime()+" 击杀:"+ playerDetailSingleMatchTemplate.getKills()+" 击倒:"+ playerDetailSingleMatchTemplate.getKillStreaks()+" 伤害:"+ playerDetailSingleMatchTemplate.getDamageDealt()+" 排名:"+ playerDetailSingleMatchTemplate.getWinPlace());

        }

        elasticSearchApiService.bulkCreateDocuments(matchesByPlayerList);



    }

    @Test
    public void testMatch() {
        String url = "https://api.playbattlegrounds.com/shards/pc-as/matches/4f5eb361-4f6c-4689-8e0b-33f7fe4790bc";
        ApiResponse apiResponse = HttpUrlConnectionApiService.doGet(url, null);
        System.out.println(apiResponse.getData());
    }

    @Test
    public void testPlayer1() {
        String url = "https://api.playbattlegrounds.com/shards/pc-as/players/account.33a98c5d521146d48ef01394c63b1e30";
        ApiResponse apiResponse = HttpUrlConnectionApiService.doGet(url, null);
        System.out.println(apiResponse.getData());
    }

    @Test
    public void testQuery(){
        SearchParam searchParam = new SearchParam();
        //searchParam.setKills(7);
        searchParam.setName("waiwainaodai");
        elasticSearchApiService.queryDocuments(searchParam);
    }

    /**
     * UTC to local
     * @param utcTime
     * @param utcTimePatten
     * @param localTimePatten
     * @return
     */
    private  String utc2Local(String utcTime, String utcTimePatten, String localTimePatten) {
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
