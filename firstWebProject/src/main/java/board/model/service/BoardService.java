package board.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.ArrayList;

import board.model.dao.BoardDao;
import board.model.dto.Board;

public class BoardService {
	private BoardDao bdao = new BoardDao();
	
	public ArrayList<Board> selectTop3() {
		Connection conn = getConnection();
		
		ArrayList<Board> list = bdao.selectTop3(conn);
		close(conn);
		return list;
		
	}
	
}
