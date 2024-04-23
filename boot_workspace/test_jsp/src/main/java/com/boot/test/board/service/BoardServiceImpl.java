package com.boot.test.board.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.test.mapper.TestMapper;
import com.boot.test.dto.Board;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private TestMapper mapper;

	@Override
	public void register(Board board) {
		mapper.insertBoard(board);		
	}

	@Override
	public void modify(Board board) {
		mapper.updateBoard(board);		
	}

	@Override
	public void remove(int boardNum) {
		mapper.deleteBoard(boardNum);		
	}

	@Override
	public Board read(int boardNum) {
		return mapper.selectBoard(boardNum);
	}

}
