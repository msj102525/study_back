package org.ict.testjpa2.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.testjpa2.board.model.dto.BoardDto;
import org.ict.testjpa2.board.model.service.BoardService;
import org.ict.testjpa2.board.model.service.ReplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/boards")
@RequiredArgsConstructor
@CrossOrigin     //리액트 애플리케이션(포트가 다름)의 자원 요청을 처리하기 위함
public class BoardController {
    private final BoardService boardService;
    private final ReplyService replyService;

    @GetMapping("/btop3")
    public ResponseEntity<List<BoardDto>> selectTop3(){
        log.info("/boards/btop3 => selectTop3()");
        return new ResponseEntity<>(boardService.selectTop3(), HttpStatus.OK);
    }

    @GetMapping("/bdetail/{boardNum}")
    public ResponseEntity<BoardDto> selectBoardDetail(@PathVariable("boardNum") int boardNum){
        log.info("/boards/" + boardNum + "요청");
        return new ResponseEntity<>(boardService.selectBoard(boardNum), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> insertBoard(@RequestBody BoardDto boardDto){
        log.info("insertBoard : " + boardDto);
        boardService.insertBoard(boardDto);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{boardNum}")  //요청 경로에 반드시 pk 에 해당하는 값을 전송해야 함 (안 보내면 에러)
    public ResponseEntity<Void> updateBoard(
            @PathVariable("boardNum") int boardNum, @RequestBody BoardDto boardDto){
        log.info("updateBoard : " + boardDto);
        boardService.updateBoard(boardDto);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{boardNum}")
    public ResponseEntity<Void> deleteBoard(@PathVariable("boardNum") int boardNum){
        log.info("update");
        boardService.deleteBoard(boardNum);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
