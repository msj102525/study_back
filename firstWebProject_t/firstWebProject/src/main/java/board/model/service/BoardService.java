package board.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

import board.model.dao.BoardDao;
import board.model.vo.Board;
import common.Paging;
import member.model.vo.Member;

public class BoardService {
	private BoardDao bdao = new BoardDao();
	
	public BoardService() {}

	public ArrayList<Board> selectTop3() {
		Connection conn = getConnection();
		ArrayList<Board> list = bdao.selectTop3(conn);
		close(conn);
		return list;
	}

	public int getListCount() {
		Connection conn = getConnection();
		int listCount = bdao.getListCount(conn);
		close(conn);
		return listCount;
	}

	public ArrayList<Board> selectList(int startRow, int endRow) {
		Connection conn = getConnection();
		ArrayList<Board> list = bdao.selectList(conn, startRow, endRow);
		close(conn);
		return list;
	}

	public int insertOriginBoard(Board board) {
		Connection conn = getConnection();
		int result = bdao.insertOriginBoard(conn, board);
		if(result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		return result;
	}

	public void addReadCount(int boardNum) {
		Connection conn = getConnection();
		int result = bdao.addReadCount(conn, boardNum);
		if(result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);		
	}

	public Board selectBoard(int boardNum) {
		Connection conn = getConnection();
		Board board = bdao.selectBoard(conn, boardNum);
		close(conn);
		return board;
	}

	public void updateReplySeq(Board reply) {
		Connection conn = getConnection();
		int result = bdao.updateReplySeq(conn, reply);
		if(result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
	}

	public int insertReply(Board reply) {
		Connection conn = getConnection();
		int result = bdao.insertReply(conn, reply);
		if(result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		return result;
	}

	public int deleteBoard(int boardNum, int boardLev) {
		Connection conn = getConnection();
		int result = bdao.deleteBoard(conn, boardNum, boardLev);
		if(result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		return result;
	}

	public int updateOriginBoard(Board board) {
		Connection conn = getConnection();
		int result = bdao.updateOriginBoard(conn, board);
		if(result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		return result;
	}
	
	public int updateReplyBoard(Board reply) {
		Connection conn = getConnection();
		int result = bdao.updateReplyBoard(conn, reply);
		if(result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		return result;
	}

	public ArrayList<Board> selectSearchTitle(String keyword, Paging paging) {
		Connection conn = getConnection();
		ArrayList<Board> list = bdao.selectSearchTitle(conn, keyword, paging);
		close(conn);
		return list;
	}

	public ArrayList<Board> selectSearchWriter(String keyword, Paging paging) {
		Connection conn = getConnection();
		ArrayList<Board> list = bdao.selectSearchWriter(conn, keyword, paging);
		close(conn);
		return list;
	}

	public ArrayList<Board> selectSearchDate(Date begin, Date end, Paging paging) {
		Connection conn = getConnection();
		ArrayList<Board> list = bdao.selectSearchDate(conn, begin, end, paging);
		close(conn);
		return list;
	}

	public int getSearchTitleCount(String keyword) {
		Connection conn = getConnection();
		int listCount = bdao.getSearchTitleCount(conn, keyword);
		close(conn);
		return listCount;
	}

	public int getSearchWriterCount(String keyword) {
		Connection conn = getConnection();
		int listCount = bdao.getSearchWriterCount(conn, keyword);
		close(conn);
		return listCount;
	}

	public int getSearchDateCount(Date begin, Date end) {
		Connection conn = getConnection();
		int listCount = bdao.getSearchDateCount(conn, begin, end);
		close(conn);
		return listCount;
	}
}











