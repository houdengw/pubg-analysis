package com.evils.entity;

import javax.persistence.*;

/**
 * Title: evils
 * CreateDate:  2018/6/28
 *
 * @author houdengw
 * @version 1.0
 */
@Entity
@Table(name="player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountId;

    private String name;
}
