package com.jm.board.controller;

import com.jm.board.entity.Board;
import com.jm.board.mapperRepository.Boardcrud;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        System.out.println("boardcrud = " + boardcrud);
        List<Board> list =  boardcrud.getAllList();
        model.addAttribute("list",list);
        return "/views/board/list";
    }
    @PostMapping("/boardDelete")
    public String boardDelete(HttpServletRequest req) { // ?idx=6
        // 로그인 안되어 있으면 인터셉터에서 처리 진입 못하게 처리
        String sIdx = (String)req.getParameter("idx");
        Integer idx = Integer.parseInt ((String)req.getParameter("idx"));
        boardcrud.boardDelete(idx); //삭제
        return "redirect:/board/list";
    }
    @GetMapping("/detail/{idx}")
    public String detail(@PathVariable("idx") int idx ,Model model) { // ?idx=6
        // 로그인 안되어 있으면 인터셉터에서 처리 진입 못하게 처리
        Board board     =boardcrud.boardDetail(idx);
        model.addAttribute("board" , board);

        return "/views/board/detail";
    }

    @PostMapping("/update/{idx}")
    public String update(@PathVariable("idx") int idx , Board board ){
        boardcrud.update(board);
        return "redirect:/board/list";
    }


}
