package com.evils.entity.dto;

/**
 * Title: evils
 * CreateDate:  2018/6/25
 *
 * @author houdengw
 * @version 1.0
 */
public class PlayerDetailSingleMatchDTO {

    private String Id;
    private String accountId;
    private String name;
    private boolean isWin;
    private String matchId;
    private int kills;
    private int killStreaks;
    private String damageDealt;
    private int headshotKills;
    private String winPointsDelta;
    private String matchTime;
    private int winPlace;


    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isWin() {
        return isWin;
    }

    public void setWin(boolean win) {
        isWin = win;
    }

    public String getMatchId() {
        return matchId;
    }

    public void setMatchId(String matchId) {
        this.matchId = matchId;
    }

    public int getHeadshotKills() {
        return headshotKills;
    }

    public void setHeadshotKills(int headshotKills) {
        this.headshotKills = headshotKills;
    }

    public String getDamageDealt() {
        return damageDealt;
    }

    public void setDamageDealt(String damageDealt) {
        this.damageDealt = damageDealt;
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

    public String getWinPointsDelta() {
        return winPointsDelta;
    }

    public void setWinPointsDelta(String winPointsDelta) {
        this.winPointsDelta = winPointsDelta;
    }

    public int getWinPlace() {
        return winPlace;
    }

    public void setWinPlace(int winPlace) {
        this.winPlace = winPlace;
    }

    public String getMatchTime() {
        return matchTime;
    }

    public void setMatchTime(String matchTime) {
        this.matchTime = matchTime;
    }
}
