package com.evils.entity.form;

import java.util.Date;

/**
 * Title: evils
 * CreateDate:  2018/7/3
 *
 * @author houdengw
 * @version 1.0
 */
public class SearchParam {

    private String name;
    private String matchDate;
    private Integer kills;
    private String orderBy;
    private String orderType;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public Integer getKills() {
        return kills;
    }

    public void setKills(Integer kills) {
        this.kills = kills;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
