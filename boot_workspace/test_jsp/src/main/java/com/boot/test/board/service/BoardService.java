package com.boot.test.board.service;

import com.boot.test.dto.Board;

public interface BoardService {
	void register(Board board);  //게시글 등록
	void modify(Board board);   //게시글 수정
	void remove(int boardNum); //게시글 삭제
	Board read(int boardNum);  //게시글 한 개 상세조회
}
