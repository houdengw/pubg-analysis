package com.evils.repository;

import com.evils.entity.Player;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Title: evils
 * CreateDate:  2018/6/28
 *
 * @author houdengw
 * @version 1.0
 */
public interface PlayerRepository extends CrudRepository<Player,Long>{

    List<Player> findAll();
}
