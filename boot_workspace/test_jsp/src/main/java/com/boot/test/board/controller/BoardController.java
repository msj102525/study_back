package com.boot.test.board.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.boot.test.board.service.BoardService;
import com.boot.test.dto.Board;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/boards")
public class BoardController {
	
	@Autowired
	private final BoardService service;
	
	//게시글 등록 처리용 메소드
	@PostMapping
	public ModelAndView boardInsert(Board board, ModelAndView mv) {
		log.info("board insert" + board.toString());
		service.register(board);
		mv.setViewName("redirect:/home");
		return mv;
	}
	
	@GetMapping
	public ModelAndView boardSelect(@RequestParam("bno") int boardNum, ModelAndView mv) {
		log.info("boardNum : " + boardNum);
		Board board = service.read(boardNum);
		log.info("board select : " + board);
		
		mv.addObject("board", board);
		mv.setViewName("board/detailView");
		return mv;
	}
	
	@PutMapping("/{bno}")   //ajax 통신
	public ResponseEntity<Void> boardUpdate(@PathVariable("bno") int boardNum,
			@Validated @RequestBody Board board) {
		service.modify(board);		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	@DeleteMapping("/{bno}")  //ajax 통신
	public ResponseEntity<Void> boardDelete(@PathVariable("bno") int boardNum) {
		service.remove(boardNum);		
		return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
	}
	
	
//	@GetMapping
//	public ModelAndView boardList() {}
	
}









