package com.jm.board.entity;

import lombok.Data;

@Data
public class Member {
    private int     midx;
    private String  memid;
    private String  password;
    private String  name;
    private int     age = 0;
    private String  gender;
    private String  email;
    private String  pimgname;
}
