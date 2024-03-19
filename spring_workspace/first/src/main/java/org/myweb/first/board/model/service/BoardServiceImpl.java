package org.myweb.first.board.model.service;

import java.util.ArrayList;

import org.myweb.first.board.model.dao.BoardDao;
import org.myweb.first.board.model.vo.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("boardSerivce")
public class BoardServiceImpl implements BoardService {
	@Autowired
	private BoardDao boardDao;
	
	@Override
	public ArrayList<Board> selectTop3() {
		return boardDao.selectTop3();
	}

}
