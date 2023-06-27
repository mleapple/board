package com.jm.board.controller;

import com.jm.board.entity.Board;
import com.jm.board.mapperRepository.Boardcrud;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * 게시판 컨트롤러
 */

@Controller
@RequestMapping("/board")
public class BoardController {
    private final Boardcrud boardcrud;
    public BoardController(Boardcrud boardcrud) {
        this.boardcrud = boardcrud;
    }
    @GetMapping("/write")
    public String boardWriting(Model model){
        return "/views/board/write";
    }
    @PostMapping("/write")
    public String write(Board board){
        System.out.println("board = " + board);
        boardcrud.boardInsert(board); // 글쓰기
        return "redirect:/board/list";
    }
    @GetMapping("/list")
    public String getRead(Model model){
     /*   List<Board>  list = new ArrayList<>();
        Board bo = new Board();
        bo.setName("양준모");
        bo.setContents("나는 로또 일등이다");
        list.add(bo);
        bo = new Board();
        bo.setName("준모2");
        bo.setContents("나는 로또 일등이다!!!");
        list.add(bo);*/
        System.out.println("boardcrud = " + boardcrud);
        List<Board> list =  boardcrud.getAllList();
        model.addAttribute("list",list);
        return "/views/board/list";

    }


}
