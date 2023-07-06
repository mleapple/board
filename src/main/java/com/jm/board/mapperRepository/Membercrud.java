package com.jm.board.mapperRepository;

import com.jm.board.entity.Board;
import com.jm.board.entity.Member;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface Membercrud {

    int memberInsert(Member member);// 등록
    Member findByLoginId(String memid , String password); // 로그인 찾기
}
