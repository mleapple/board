package com.jm.board.mapperRepository;

import com.jm.board.entity.Board;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Mapper
@Repository
public interface Boardcrud {

    int boardInsert(Board board);// 등록
    List<Board> getAllList(); // 전체 리스트

    int boardDelete(int idx); // 삭제
    Board boardDetail(Integer idx); // 상세

    int update(Board board); // 수정

    int  boardCount(int idx); // 카운트
}
