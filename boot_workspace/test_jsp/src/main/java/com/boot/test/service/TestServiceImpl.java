package com.boot.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.boot.test.dto.Board;
import com.boot.test.mapper.TestMapper;

@Service
public class TestServiceImpl implements TestService {

	@Autowired
	private TestMapper testMapper;
	
	@Override
	public List<Board> selectList() {		
		return testMapper.selectBoardList();
	}

}
