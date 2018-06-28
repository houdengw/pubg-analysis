package com.evils.entity;

import java.util.Date;

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
    private String kills;
    private String killStreaks;
    private String damageDealt;
    private String headshotKills;
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

    public String getKills() {
        return kills;
    }

    public void setKills(String kills) {
        this.kills = kills;
    }

    public String getKillStreaks() {
        return killStreaks;
    }

    public void setKillStreaks(String killStreaks) {
        this.killStreaks = killStreaks;
    }

    public String getDamageDealt() {
        return damageDealt;
    }

    public void setDamageDealt(String damageDealt) {
        this.damageDealt = damageDealt;
    }

    public String getHeadshotKills() {
        return headshotKills;
    }

    public void setHeadshotKills(String headshotKills) {
        this.headshotKills = headshotKills;
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