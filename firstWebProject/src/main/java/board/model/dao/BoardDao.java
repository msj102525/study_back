package board.model.dao;

import static common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import board.model.dto.Board;

public class BoardDao {

	public ArrayList<Board> selectTop3(Connection conn) {
		ArrayList<Board> list = new ArrayList<Board>();
		String query = "SELECT * " + "FROM (SELECT ROWNUM rnum, board_num, board_title, board_readcount "
				+ "FROM (SELECT * FROM board " + "WHERE board_lev = 1 " + "ORDER BY BOARD_READCOUNT desc)) "
				+ "WHERE rnum BETWEEN 1 AND 3";
		Statement stmt = null;
		ResultSet rset = null;
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			while(rset.next()) {
				Board board = new Board();
				
				board.setBoardNum(rset.getInt("board_num"));
				board.setBoardTitle(rset.getString("board_title"));
				board.setBoardReadcount(rset.getInt("board_readcount"));
				
				list.add(board);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}

		return list;
	}

}
