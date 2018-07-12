package com.evils.service.impl;

import com.evils.base.ElasticSearchApiService;
import com.evils.entity.dto.PlayerDetailSingleMatchDTO;
import com.evils.entity.form.SearchParam;
import com.evils.service.IMatchService;
import com.evils.service.ServiceListResult;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Title: evils
 * CreateDate:  2018/7/3
 *
 * @author houdengw
 * @version 1.0
 */
@Service
public class MatchServiceImpl implements IMatchService {

    @Autowired
    private ElasticSearchApiService elasticSearchApiService;

    @Override
    public ServiceListResult<PlayerDetailSingleMatchDTO> getMatches(SearchParam searchParam) {
        SearchHits searchHits = elasticSearchApiService.queryDocuments(searchParam);
        List<PlayerDetailSingleMatchDTO> playerDetailSingleMatchDTOList = new ArrayList<>();
        for(SearchHit searchHit:searchHits){
            PlayerDetailSingleMatchDTO playerDetailSingleMatchDTO = new PlayerDetailSingleMatchDTO();
            playerDetailSingleMatchDTO.setName(String.valueOf(searchHit.getSource().get("name")));
            playerDetailSingleMatchDTO.setKills(Integer.parseInt(String.valueOf(searchHit.getSource().get("kills"))));
            playerDetailSingleMatchDTO.setKillStreaks(Integer.parseInt(String.valueOf(searchHit.getSource().get("killStreaks"))));
            playerDetailSingleMatchDTO.setMatchTime(String.valueOf(searchHit.getSource().get("matchTime")));
            playerDetailSingleMatchDTO.setWinPointsDelta(String.valueOf(searchHit.getSource().get("winPointsDelta")));
            playerDetailSingleMatchDTO.setHeadshotKills(Integer.parseInt(String.valueOf(searchHit.getSource().get("headshotKills"))));
            playerDetailSingleMatchDTO.setDamageDealt(String.valueOf(searchHit.getSource().get("damageDealt")));
            playerDetailSingleMatchDTO.setWinPlace(Integer.parseInt(String.valueOf(searchHit.getSource().get("winPlace"))));
            playerDetailSingleMatchDTO.setGameMode(String.valueOf(searchHit.getSource().get("gameMode")));
            playerDetailSingleMatchDTO.setMapName(String.valueOf(searchHit.getSource().get("mapName")));
            playerDetailSingleMatchDTOList.add(playerDetailSingleMatchDTO);
        }
        return new ServiceListResult<PlayerDetailSingleMatchDTO>(searchHits.getTotalHits(),playerDetailSingleMatchDTOList);
    }
}
