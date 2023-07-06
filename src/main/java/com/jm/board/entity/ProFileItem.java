package com.jm.board.entity;

import com.jm.board.upload.UploadFile;
import lombok.Data;

import java.util.List;

@Data
public class ProFileItem {
    private int midx;
    private String nicname;
    private String attachfilename;
    private List<Fitems> imageFiles;
}
