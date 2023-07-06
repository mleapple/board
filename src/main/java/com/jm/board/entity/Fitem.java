package com.jm.board.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.List;

@Data
public class Fitem {

    private int sidx;
    private int fkey;
    private int midx;
    private String    storeFileName; // 첨부파일
    private String    uuidFileName; // 첨부파일
    private Timestamp regdate;
    private Timestamp moddate;
    //private List<MultipartFile> imageFiles; // 여러이미지 파일
}
