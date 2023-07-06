package com.jm.board.entity;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class ProfileForm {
    private int    fidx;
    private int midx;
    private String nicname;
    private MultipartFile attachfile; // 첨부파일
    private List<MultipartFile> imageFiles; // 여러이미지 파일
}
