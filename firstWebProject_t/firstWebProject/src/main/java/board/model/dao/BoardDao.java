package board.model.dao;

import static common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import board.model.vo.Board;
import common.Paging;
import member.model.vo.Member;

public class BoardDao {
	public BoardDao() {}

	public ArrayList<Board> selectTop3(Connection conn) {
		ArrayList<Board> list = new ArrayList<Board>();
		Statement stmt = null;
		ResultSet rset = null;
		
		String query = "select * "
				+ "from (select rownum rnum, board_num, board_title, board_readcount "
				+ "    from (select * from board "
				+ "          where board_lev = 1 "
				+ "          order by board_readcount desc)) "
				+ "where rnum <= 3";
		
		try {
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(query);
			
			while(rset.next()) {
				Board board = new Board();
				
				board.setBoardNum(rset.getInt("board_num"));
				board.setBoardTitle(rset.getString("board_title"));
				board.setBoardReadCount(rset.getInt("board_readcount"));
				
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

	public int getListCount(Connection conn) {
		int listCount = 0;
		Statement stmt = null;
		ResultSet rset = null;
		
		String query = "select count(*) from board";
		
		try {
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(query);
			
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(stmt);
		}		
		
		return listCount;
	}

	public ArrayList<Board> selectList(Connection conn, int startRow, int endRow) {
		ArrayList<Board> list = new ArrayList<Board>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "select * "
				+ "from (select rownum rnum, board_num, board_writer, board_title, "
				+ "board_content, "
				+ "              board_original_filename, board_rename_filename, "
				+ "board_ref, board_reply_ref, "
				+ "              board_lev, board_reply_seq, board_readcount, board_date "
				+ "        from (select * from board "
				+ "             order by board_ref desc, board_reply_ref desc, "
				+ "board_lev asc, board_reply_seq asc)) "
				+ "where rnum >= ? and rnum <= ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, startRow);
			pstmt.setInt(2, endRow);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Board board = new Board();
				
				board.setBoardNum(rset.getInt("board_num"));
				board.setBoardTitle(rset.getString("board_title"));
				board.setBoardReadCount(rset.getInt("board_readcount"));
				board.setBoardWriter(rset.getString("board_writer"));
				board.setBoardDate(rset.getDate("board_date"));
				board.setBoardOriginalFileName(rset.getString("board_original_filename"));
				board.setBoardRenameFileName(rset.getString("board_rename_filename"));
				board.setBoardContent(rset.getString("board_content"));
				board.setBoardLev(rset.getInt("board_lev"));
				board.setBoardRef(rset.getInt("board_ref"));
				board.setBoardReplyRef(rset.getInt("board_reply_ref"));
				board.setBoardReplySeq(rset.getInt("board_reply_seq"));
				
				list.add(board);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int insertOriginBoard(Connection conn, Board board) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "insert into board values  "
				+ "((select max(board_num) + 1 from board), ?, ?, ?, ?, ?,  "
				+ "(select max(board_num) + 1 from board), null, default,  "
				+ "default, default, default)";
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, board.getBoardWriter());
			pstmt.setString(2, board.getBoardTitle());
			pstmt.setString(3, board.getBoardContent());
			pstmt.setString(4, board.getBoardOriginalFileName());
			pstmt.setString(5, board.getBoardRenameFileName());
			
			result = pstmt.executeUpdate();			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}		
		
		return result;
	}

	public int addReadCount(Connection conn, int boardNum) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "update board "
				+ "set board_readcount = board_readcount + 1 "
				+ "where board_num = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNum);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public Board selectBoard(Connection conn, int boardNum) {
		Board board = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "select * from board "
				+ "where board_num = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNum);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				board = new Board();
				
				board.setBoardNum(rset.getInt("board_num"));
				board.setBoardTitle(rset.getString("board_title"));
				board.setBoardReadCount(rset.getInt("board_readcount"));
				board.setBoardWriter(rset.getString("board_writer"));
				board.setBoardDate(rset.getDate("board_date"));
				board.setBoardOriginalFileName(rset.getString("board_original_filename"));
				board.setBoardRenameFileName(rset.getString("board_rename_filename"));
				board.setBoardContent(rset.getString("board_content"));
				board.setBoardLev(rset.getInt("board_lev"));
				board.setBoardRef(rset.getInt("board_ref"));
				board.setBoardReplyRef(rset.getInt("board_reply_ref"));
				board.setBoardReplySeq(rset.getInt("board_reply_seq"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return board;
	}

	public int updateReplySeq(Connection conn, Board reply) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = null;
		
		//새로 등록할 댓글이 원글의 댓글일 때
		if(reply.getBoardLev() == 2) {
			query = "update board "
					+ "set board_reply_seq = board_reply_seq + 1 "
					+ "where board_ref = ? and board_lev = ?";
		}
		
		//새로 등록할 댓글이 댓글의 댓글일 때
		if(reply.getBoardLev() == 3) {
			query = "update board "
					+ "set board_reply_seq = board_reply_seq + 1 "
					+ "where board_ref = ? and board_lev = ? "
					+ "and board_reply_ref = ?";
		}
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, reply.getBoardRef());
			pstmt.setInt(2, reply.getBoardLev());
			
			if(reply.getBoardLev() == 3) {
				pstmt.setInt(3, reply.getBoardReplyRef());
			}
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int insertReply(Connection conn, Board reply) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = null;
		
		if(reply.getBoardLev() == 2) {
			query = "insert into board values "
					+ "((select max(board_num) + 1 from board), ?, ?, ?, null, null, ?, "
					+ "(select max(board_num) + 1 from board), 2, ?, default, default)";
		}
		
		if(reply.getBoardLev() == 3) {
			query = "insert into board values "
					+ "((select max(board_num) + 1 from board), ?, ?, ?, null, null, ?, "
					+ "?, 3, ?, default, default)";
		}
		 
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, reply.getBoardWriter());
			pstmt.setString(2, reply.getBoardTitle());
			pstmt.setString(3, reply.getBoardContent());
			pstmt.setInt(4, reply.getBoardRef());
			
			if(reply.getBoardLev() == 2) {
				pstmt.setInt(5, reply.getBoardReplySeq());
			}
			
			if(reply.getBoardLev() == 3) {
				pstmt.setInt(5, reply.getBoardReplyRef());
				pstmt.setInt(6, reply.getBoardReplySeq());
			}
			
			result = pstmt.executeUpdate();			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}		
		
		return result;
	}

	public int deleteBoard(Connection conn, int boardNum, int boardLev) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "delete from board ";
		
		if(boardLev == 1) {
			query += "where board_ref = ?";
		}
		
		if(boardLev == 2) {
			query += "where board_reply_ref = ?";
		}
		
		if(boardLev == 3) {
			query += "where board_num = ?";
		}
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, boardNum);
			
			result = pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int updateOriginBoard(Connection conn, Board board) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "update board  "
				+ "set board_title = ?, "
				+ "    board_content = ?, "
				+ "    board_original_filename = ?, "
				+ "    board_rename_filename = ? "
				+ "where board_num = ? ";
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, board.getBoardTitle());			
			pstmt.setString(2, board.getBoardContent());
			pstmt.setString(3, board.getBoardOriginalFileName());
			pstmt.setString(4, board.getBoardRenameFileName());
			pstmt.setInt(5, board.getBoardNum());
			
			result = pstmt.executeUpdate();			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}		
		
		return result;
	}
	
	public int updateReplyBoard(Connection conn, Board reply) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "update board  "
				+ "set board_title = ?, "
				+ "    board_content = ? "				
				+ "where board_num = ? ";
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, reply.getBoardTitle());
			pstmt.setString(2, reply.getBoardContent());			
			pstmt.setInt(3, reply.getBoardNum());
			
			result = pstmt.executeUpdate();			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}		
		
		return result;
	}

	public ArrayList<Board> selectSearchTitle(Connection conn, String keyword, Paging paging) {
		ArrayList<Board> list = new ArrayList<Board>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//검색할 title 에 대해 원글만 키워드 값이 유사한 형태의 값들을 선택
		String query = "select * "
				+ "from (select rownum rnum, board_num, board_writer, board_title, "
				+ "           board_content, board_original_filename, board_rename_filename, "
				+ "           board_ref, board_reply_ref, "
				+ "           board_lev, board_reply_seq, board_readcount, board_date "
				+ "      from (select * from board "
				+ "            where board_lev = 1 and board_title like ? "
				+ "             order by board_ref desc, board_reply_ref desc, "
				+ "                      board_lev asc, board_reply_seq asc)) "
				+ "where rnum >= ? and rnum <= ?";
				
		try {
			pstmt = conn.prepareStatement(query);	
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, paging.getStartRow());
			pstmt.setInt(3, paging.getEndRow());
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Board board = new Board();
				
				board.setBoardNum(rset.getInt("board_num"));
				board.setBoardTitle(rset.getString("board_title"));
				board.setBoardReadCount(rset.getInt("board_readcount"));
				board.setBoardWriter(rset.getString("board_writer"));
				board.setBoardDate(rset.getDate("board_date"));
				board.setBoardOriginalFileName(rset.getString("board_original_filename"));
				board.setBoardRenameFileName(rset.getString("board_rename_filename"));
				board.setBoardContent(rset.getString("board_content"));
				board.setBoardLev(rset.getInt("board_lev"));
				board.setBoardRef(rset.getInt("board_ref"));
				board.setBoardReplyRef(rset.getInt("board_reply_ref"));
				board.setBoardReplySeq(rset.getInt("board_reply_seq"));
				
				list.add(board);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public ArrayList<Board> selectSearchWriter(Connection conn, String keyword, Paging paging) {
		ArrayList<Board> list = new ArrayList<Board>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//검색할 title 에 대해 원글만 키워드 값이 유사한 형태의 값들을 선택
		//검색할 title 에 대해 원글만 키워드 값이 유사한 형태의 값들을 선택
		String query = "select * "
				+ "from (select rownum rnum, board_num, board_writer, board_title, "
				+ "           board_content, board_original_filename, board_rename_filename, "
				+ "           board_ref, board_reply_ref, "
				+ "           board_lev, board_reply_seq, board_readcount, board_date "
				+ "      from (select * from board "
				+ "            where board_lev = 1 and board_writer like ? "
				+ "             order by board_ref desc, board_reply_ref desc, "
				+ "                      board_lev asc, board_reply_seq asc)) "
				+ "where rnum >= ? and rnum <= ?";
				
		try {
			pstmt = conn.prepareStatement(query);	
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, paging.getStartRow());
			pstmt.setInt(3, paging.getEndRow());		
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Board board = new Board();
				
				board.setBoardNum(rset.getInt("board_num"));
				board.setBoardTitle(rset.getString("board_title"));
				board.setBoardReadCount(rset.getInt("board_readcount"));
				board.setBoardWriter(rset.getString("board_writer"));
				board.setBoardDate(rset.getDate("board_date"));
				board.setBoardOriginalFileName(rset.getString("board_original_filename"));
				board.setBoardRenameFileName(rset.getString("board_rename_filename"));
				board.setBoardContent(rset.getString("board_content"));
				board.setBoardLev(rset.getInt("board_lev"));
				board.setBoardRef(rset.getInt("board_ref"));
				board.setBoardReplyRef(rset.getInt("board_reply_ref"));
				board.setBoardReplySeq(rset.getInt("board_reply_seq"));
				
				list.add(board);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public ArrayList<Board> selectSearchDate(Connection conn, Date begin, Date end, Paging paging) {
		ArrayList<Board> list = new ArrayList<Board>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//검색할 title 에 대해 원글만 키워드 값이 유사한 형태의 값들을 선택
		//검색할 title 에 대해 원글만 키워드 값이 유사한 형태의 값들을 선택
		String query = "select * "
				+ "from (select rownum rnum, board_num, board_writer, board_title, "
				+ "           board_content, board_original_filename, board_rename_filename, "
				+ "           board_ref, board_reply_ref, "
				+ "           board_lev, board_reply_seq, board_readcount, board_date "
				+ "      from (select * from board "
				+ "            where board_lev = 1 and board_date between ? and ? "
				+ "             order by board_ref desc, board_reply_ref desc, "
				+ "                      board_lev asc, board_reply_seq asc)) "
				+ "where rnum >= ? and rnum <= ?";
				
		try {
			pstmt = conn.prepareStatement(query);	
			pstmt.setDate(1, begin);
			pstmt.setDate(2, end);
			pstmt.setInt(3, paging.getStartRow());
			pstmt.setInt(4, paging.getEndRow());		
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Board board = new Board();
				
				board.setBoardNum(rset.getInt("board_num"));
				board.setBoardTitle(rset.getString("board_title"));
				board.setBoardReadCount(rset.getInt("board_readcount"));
				board.setBoardWriter(rset.getString("board_writer"));
				board.setBoardDate(rset.getDate("board_date"));
				board.setBoardOriginalFileName(rset.getString("board_original_filename"));
				board.setBoardRenameFileName(rset.getString("board_rename_filename"));
				board.setBoardContent(rset.getString("board_content"));
				board.setBoardLev(rset.getInt("board_lev"));
				board.setBoardRef(rset.getInt("board_ref"));
				board.setBoardReplyRef(rset.getInt("board_reply_ref"));
				board.setBoardReplySeq(rset.getInt("board_reply_seq"));
				
				list.add(board);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int getSearchTitleCount(Connection conn, String keyword) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "select count(*) from board where board_lev = 1 and board_title like ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + keyword + "%");
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}		
		
		return listCount;
	}

	public int getSearchWriterCount(Connection conn, String keyword) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "select count(*) from board where board_lev = 1 and board_writer like ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + keyword + "%");
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}		
		
		return listCount;
	}

	public int getSearchDateCount(Connection conn, Date begin, Date end) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "select count(*) from board where board_lev = 1 and board_date between ? and ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setDate(1, begin);
			pstmt.setDate(2, end);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				listCount = rset.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}		
		
		return listCount;
	}
	
	
}














