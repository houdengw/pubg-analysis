package com.evils.service.impl;

import com.evils.entity.Player;
import com.evils.repository.PlayerRepository;
import com.evils.service.IPlayerService;
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

    @Override
    public List<Player> findAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }
}
