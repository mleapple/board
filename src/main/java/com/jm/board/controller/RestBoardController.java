package com.jm.board.controller;

import com.jm.board.entity.Board;
import com.jm.board.mapperRepository.Boardcrud;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/restbo")
@RestController
public class RestBoardController {
    private final Boardcrud boardcrud;

    public RestBoardController(Boardcrud boardcrud){
        this.boardcrud = boardcrud;
    }
    @PostMapping("/new")
    public @ResponseBody Map writebo(Board board){//글쓰기
        Map result = new HashMap();
        int n = boardcrud.boardInsert(board);
        result.put("status", n>0 ?"SUCCESS":"FAIL");
        return result ;
    }
    @GetMapping("/all")
    public @ResponseBody List<Board> readbo(){
        List<Board> result = boardcrud.getAllList();
        return  result;
    }
    @GetMapping("/{idx}")
    public @ResponseBody Board detailbo(@PathVariable("idx") int idx){

        Board board   =boardcrud.boardDetail(idx);
        return  board;
    }

    @DeleteMapping("/{idx}")
    public @ResponseBody Map deletebo(@PathVariable("idx") int idx){
       //삭제
        Map result = new HashMap();
        int n =  boardcrud.boardDelete(idx);
        result.put("status", n>0 ?"SUCCESS":"FAIL");
        return  result;
    }

    @PutMapping("/update")
    public @ResponseBody Map updatePut( @RequestBody  Board board ){
        // 유효성 체크없이
      /*  if(board.getIdx() >0) {
            boardcrud.update(board);
        }*/
        Map result = new HashMap();
        int n =  boardcrud.update(board);
        result.put("status", n>0 ?"SUCCESS":"FAIL");

        return result;
    }



}
