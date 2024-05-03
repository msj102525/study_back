package org.ict.testjpa2.board.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.testjpa2.board.model.dto.BoardDto;
import org.ict.testjpa2.board.model.service.BoardService;
import org.ict.testjpa2.board.model.service.ReplyService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping("/list")
    public ResponseEntity<List<BoardDto>> selectList(@RequestParam(name="page") int page,
                                                     @RequestParam(name="limit") int limit){
        log.info("/boards/list : " + page + ", " + limit);
        //JPA 가 제공하는 Pageable 객체를 사용함
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "boardNum"));
        //페이지에 출력할 목록 조회해 옴    => 응답 처리
        return new ResponseEntity<>(boardService.selectList(pageable), HttpStatus.OK);
    }

    @GetMapping("/title")
    public ResponseEntity<List<BoardDto>> selectSearchTitle(
            @RequestParam(name="keyword") String keyword,  @RequestParam(name="page") int page,
                                                     @RequestParam(name="limit") int limit){
        log.info("/boards/title : " + keyword + ", " + page + ", " + limit);
        //JPA 가 제공하는 Pageable 객체를 사용함
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "boardNum"));
        //페이지에 출력할 목록 조회해 옴    => 응답 처리
        return new ResponseEntity<>(boardService.selectSearchTitle(keyword, pageable), HttpStatus.OK);
    }

    @GetMapping("/writer")
    public ResponseEntity<List<BoardDto>> selectSearchWriter(
            @RequestParam(name="keyword") String keyword,  @RequestParam(name="page") int page,
                                                     @RequestParam(name="limit") int limit){
        log.info("/boards/list : " + page + ", " + limit);
        //JPA 가 제공하는 Pageable 객체를 사용함
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "boardNum"));
        //페이지에 출력할 목록 조회해 옴    => 응답 처리
        return new ResponseEntity<>(boardService.selectSearchWriter(keyword, pageable), HttpStatus.OK);
    }

    @GetMapping("/date")
    public ResponseEntity<List<BoardDto>> selectSearchDate(
            @RequestParam(name="begin") java.sql.Date begin, @RequestParam(name="end") java.sql.Date end,
            @RequestParam(name="page") int page, @RequestParam(name="limit") int limit){
        log.info("/boards/list : " + page + ", " + limit);
        //JPA 가 제공하는 Pageable 객체를 사용함
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "boardNum"));
        //페이지에 출력할 목록 조회해 옴    => 응답 처리
        return new ResponseEntity<>(boardService.selectSearchDate(begin, end, pageable), HttpStatus.OK);
    }

    //목록 갯수 조회용
    @GetMapping("/countBoards")
    public ResponseEntity<Long> getCountBoards(){
        return new ResponseEntity<>(boardService.getCountBoards(), HttpStatus.OK);
    }

    //제목 검색 목록 갯수 조회용
    @GetMapping("/countSearchTitle")
    public ResponseEntity<Long> getCountSearchTitle(@RequestParam(name="keyword") String keyword){
        return new ResponseEntity<>(boardService.getCountSearchTitle(keyword), HttpStatus.OK);
    }

    //작성자 검색 목록 갯수 조회용
    @GetMapping("/countSearchWriter")
    public ResponseEntity<Long> getCountSearchWriter(@RequestParam(name="keyword") String keyword){
        return new ResponseEntity<>(boardService.getCountSearchWriter(keyword), HttpStatus.OK);
    }

    //등록날짜 검색 목록 갯수 조회용
    @GetMapping("/countSearchDate")
    public ResponseEntity<Long> getCountSearchDate(
            @RequestParam(name="begin") java.sql.Date begin,  @RequestParam(name="end") java.sql.Date end){
        return new ResponseEntity<>(boardService.getCountSearchDate(begin, end), HttpStatus.OK);
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
