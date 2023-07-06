package com.jm.board.entity;

import lombok.Data;

import java.util.Date;

@Data
public class ProfileVo {
    private int     fidx;
    private int     midx ;
    private String  nicname;
    private Date    regdate ;
    private Date    moddate ;
}
