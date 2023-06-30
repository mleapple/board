package com.jm.board.mapperRepository;

import com.jm.board.entity.Board;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Mapper
@Repository
public interface Boardcrud {

    void boardInsert(Board board);
    List<Board> getAllList();

    void boardDelete(int idx);
    Board boardDetail(Integer idx);

    void update(Board board);
}
