package com.evils.entity.dto;

/**
 * Title: evils
 * CreateDate:  2018/7/8
 *
 * @author houdengw
 * @version 1.0
 */
public class PlayerDetailSingleMatchDTO {

    private String name;
    private int kills;
    private int killStreaks;
    private String damageDealt;
    private int headshotKills;
    private String winPointsDelta;
    private String matchTime;
    private int winPlace;
    private String gameMode;
    private String mapName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getKillStreaks() {
        return killStreaks;
    }

    public void setKillStreaks(int killStreaks) {
        this.killStreaks = killStreaks;
    }

    public String getDamageDealt() {
        return damageDealt;
    }

    public void setDamageDealt(String damageDealt) {
        this.damageDealt = damageDealt;
    }

    public int getHeadshotKills() {
        return headshotKills;
    }

    public void setHeadshotKills(int headshotKills) {
        this.headshotKills = headshotKills;
    }

    public String getWinPointsDelta() {
        return winPointsDelta;
    }

    public void setWinPointsDelta(String winPointsDelta) {
        this.winPointsDelta = winPointsDelta;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }

    public int getWinPlace() {
        return winPlace;
    }

    public void setWinPlace(int winPlace) {
        this.winPlace = winPlace;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getMapName() {
        return mapName;
    }

    public void setMapName(String mapName) {
        this.mapName = mapName;
    }
}
