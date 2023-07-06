package com.jm.board.entity;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class Fitems {
    private int       sidx;
    private int       fkey;
    private int       midx;
    private String    storeFileName; // 첨부파일
    private String    uuidFileName; // 첨부파일
    private Timestamp regdate;
    private Timestamp moddate;
}

