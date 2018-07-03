package com.evils.service;

import com.evils.entity.Player;

import java.util.List;

/**
 * Title: evils
 * CreateDate:  2018/6/28
 *
 * @author houdengw
 * @version 1.0
 */
public interface IPlayerService {

    List<Player> findAllPlayers();

    Player savePlayer(Player player);

    List<Player> findPlayer(String name);
}
