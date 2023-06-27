package com.jm.board.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Board {

    private int  idx;
    private String name;
    private String title ;
    private String contents;
    private int count;
    private Timestamp date;


}
