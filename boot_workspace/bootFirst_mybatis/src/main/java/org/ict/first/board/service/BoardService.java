package org.ict.first.board.service;

import java.util.ArrayList;
import java.util.List;

import org.ict.first.board.mapper.BoardMapper;
import org.ict.first.board.vo.Board;
import org.ict.first.common.Paging;
import org.ict.first.common.Search;
import org.ict.first.common.SearchDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("boardService")
@Transactional
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;	
	
	public ArrayList<Board> selectTop3() {
		List<Board> list = boardMapper.selectTop3();
		return (ArrayList<Board>)list;
	}
	
	public int selectListCount() {
		return boardMapper.getListCount();
	}
	
	public ArrayList<Board> selectList(Paging paging) {
		List<Board> list = boardMapper.selectList(paging);
		return (ArrayList<Board>)list;
	}
	
	public int insertOriginBoard(Board board) {
		return boardMapper.insertOriginBoard(board);
	}
	
	public void updateAddReadCount(int boardNum) {
		boardMapper.addReadCount(boardNum);	
	}
	
	public Board selectBoard(int boardNum) {
		return boardMapper.selectBoard(boardNum);
	}
	
	public int updateReplySeq(Board reply) {
		int result = 0;
		
		if(reply.getBoardLev() == 2) {
			result = boardMapper.updateReplySeq1(reply);
		}
		if(reply.getBoardLev() == 3) {
			result = boardMapper.updateReplySeq2(reply);
		}
		
		return result;		
	}
	
	public int insertReply(Board reply) {
		int result = 0;
		
		if(reply.getBoardLev() == 2) {
			result = boardMapper.insertReply1(reply);
		}
		if(reply.getBoardLev() == 3) {
			result = boardMapper.insertReply2(reply);
		}
		
		return result;		
	}
	
	public int deleteBoard(Board board) {
		return boardMapper.deleteBoard(board);
	}
	
	public int updateOriginBoard(Board board) {
		return boardMapper.updateOrigin(board);
	}	
	
	public int updateReplyBoard(Board reply) {
		return boardMapper.updateReply(reply);
	}
	
	public ArrayList<Board> selectSearchTitle(Search search) {
		List<Board> list = boardMapper.selectSearchTitle( search);
		return (ArrayList<Board>)list;
	}
	
	public ArrayList<Board> selectSearchWriter(Search search) {
		List<Board> list = boardMapper.selectSearchWriter(search);
		return (ArrayList<Board>)list;
	}
	
	public ArrayList<Board> selectSearchDate(Search search) {
		List<Board> list = boardMapper.selectSearchDate(search);
		return (ArrayList<Board>)list;
	}
	
	public int selectSearchTitleCount(String keyword) {
		return boardMapper.getSearchTitleCount(keyword);
	}
	
	public int selectSearchWriterCount(String keyword) {
		return boardMapper.getSearchWriterCount(keyword);
	}
	
	public int selectSearchDateCount(SearchDate date) {
		return boardMapper.getSearchDateCount(date);
	}
}











