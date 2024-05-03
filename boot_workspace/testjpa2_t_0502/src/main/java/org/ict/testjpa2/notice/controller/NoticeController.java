package org.ict.testjpa2.notice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ict.testjpa2.common.SearchDate;
import org.ict.testjpa2.notice.model.dto.NoticeDto;
import org.ict.testjpa2.notice.model.service.NoticeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/notices")
@RequiredArgsConstructor
@CrossOrigin     //리액트 애플리케이션(포트가 다름)의 자원 요청을 처리하기 위함
public class NoticeController {
    private final NoticeService noticeService;
    
    //요청 메서드 구현부 -----------------------------------
    @GetMapping("/ntop3")
    public ResponseEntity<List<NoticeDto>> selectNewTop3(){
        log.info("/notices/ntop3 => selectNewTop3()");
        return new ResponseEntity<>(noticeService.selectTop3(), HttpStatus.OK);
    }

    @GetMapping("/list")
    public ResponseEntity<ArrayList<NoticeDto>> selectList(@RequestParam(name="page") int page,
                                                           @RequestParam(name="limit") int limit){
        log.info("/notices/list : " + page + ", " + limit);
        //JPA 가 제공하는 Pageable 객체를 사용함
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "noticeNo"));
        //페이지에 출력할 목록 조회해 옴    => 응답 처리
        return new ResponseEntity<>(noticeService.selectList(pageable), HttpStatus.OK);
    }

    @GetMapping("/title")
    public ResponseEntity<List<NoticeDto>> selectSearchTitle(
            @RequestParam(name="keyword") String keyword,  @RequestParam(name="page") int page,
            @RequestParam(name="limit") int limit){
        log.info("/notices/title : " + keyword + ", " + page + ", " + limit);
        //JPA 가 제공하는 Pageable 객체를 사용함
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "noticeNo"));
        //페이지에 출력할 목록 조회해 옴    => 응답 처리
        return new ResponseEntity<>(noticeService.selectSearchTitle(keyword, pageable), HttpStatus.OK);
    }

    @GetMapping("/content")
    public ResponseEntity<List<NoticeDto>> selectSearchContent(
            @RequestParam(name="keyword") String keyword,  @RequestParam(name="page") int page,
            @RequestParam(name="limit") int limit){
        log.info("/notices/list : " + page + ", " + limit);
        //JPA 가 제공하는 Pageable 객체를 사용함
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "noticeNo"));
        //페이지에 출력할 목록 조회해 옴    => 응답 처리
        return new ResponseEntity<>(noticeService.selectSearchContent(keyword, pageable), HttpStatus.OK);
    }

    @GetMapping("/date")
    public ResponseEntity<List<NoticeDto>> selectSearchDate(
            @RequestParam SearchDate searchDate,
            @RequestParam(name="page") int page, @RequestParam(name="limit") int limit){
        log.info("/notices/list : " + page + ", " + limit);
        //JPA 가 제공하는 Pageable 객체를 사용함
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "noticeNo"));
        //페이지에 출력할 목록 조회해 옴    => 응답 처리
        return new ResponseEntity<>(noticeService.selectSearchDate(searchDate, pageable), HttpStatus.OK);
    }

    //목록 갯수 조회용
    @GetMapping("/count")
    public ResponseEntity<Long> getCountList(){
        return new ResponseEntity<>(noticeService.selectListCount(), HttpStatus.OK);
    }

    //제목 검색 목록 갯수 조회용
    @GetMapping("/countSearchTitle")
    public ResponseEntity<Long> getCountSearchTitle(@RequestParam(name="keyword") String keyword){
        return new ResponseEntity<>(noticeService.getSearchTitleCount(keyword), HttpStatus.OK);
    }

    //작성자 검색 목록 갯수 조회용
    @GetMapping("/countSearchContent")
    public ResponseEntity<Long> getCountSearchWriter(@RequestParam(name="keyword") String keyword){
        return new ResponseEntity<>(noticeService.getSearchContentCount(keyword), HttpStatus.OK);
    }

    //등록날짜 검색 목록 갯수 조회용
    @GetMapping("/countSearchDate")
    public ResponseEntity<Long> getCountSearchDate(
            @RequestParam SearchDate searchDate){
        return new ResponseEntity<>(noticeService.getSearchDateCount(searchDate), HttpStatus.OK);
    }

    @GetMapping("/ndetail/{noticeNo}")
    public ResponseEntity<NoticeDto> selectNoticeDetail(@PathVariable("noticeNo") int noticeNo){
        log.info("/notices/" + noticeNo + "요청");
        return new ResponseEntity<>(noticeService.selectNotice(noticeNo), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> insertBoard(@RequestBody NoticeDto noticeDto){
        log.info("insertBoard : " + noticeDto);
        noticeService.insertNotice(noticeDto);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{noticeNo}")  //요청 경로에 반드시 pk 에 해당하는 값을 전송해야 함 (안 보내면 에러)
    public ResponseEntity<Void> updateNotice(
            @PathVariable("noticeNo") int noticeNo, @RequestBody NoticeDto noticeDto){
        log.info("updateNotice : " + noticeDto);
        noticeService.updateNotice(noticeDto);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{noticeNo}")
    public ResponseEntity<Void> deleteNotice(@PathVariable("noticeNo") int noticeNo){
        log.info("deleteNotice : " + noticeNo);
        noticeService.deleteNotice(noticeNo);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
