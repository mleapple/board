package com.jm.board.mapperRepository;

import com.jm.board.entity.Fitem;
import com.jm.board.entity.Fitems;
import com.jm.board.entity.Member;
import com.jm.board.entity.ProfileVo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;


@Mapper
@Repository
public interface Profilecrud {

    int profileInsert(ProfileVo profileVo);// 등록

    int filePathInsert(Fitem fitem);// 등록

    int imgfilePathInsert(Fitems fitem);// 등록

    ProfileVo profilefindById(Long id);
    Fitem filePathfilefindById(Long id);

    List<Fitems> imgfilePathfindById(Long id);
}
