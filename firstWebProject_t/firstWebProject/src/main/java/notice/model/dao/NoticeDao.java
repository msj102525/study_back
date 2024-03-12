package notice.model.dao;

import static common.JDBCTemplate.close;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import common.Paging;
import notice.model.vo.Notice;

public class NoticeDao {

	//공지사항 페이지 단위로 목록 조회 : 공지사항 목록보기용
	public ArrayList<Notice> selectList(Connection conn, Paging paging){
		ArrayList<Notice> list = new ArrayList<Notice>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "select * "
				+ "from (select rownum rnum, noticeno, noticetitle, noticedate, "
				+ "            noticewriter, noticecontent, original_filepath, rename_filepath, "
				+ "            importance, imp_end_date, readcount "
				+ "      from (select * from notice "
				+ "            order by importance desc, noticedate desc, noticeno desc)"
				+ "     ) "
				+ "where rnum between ? and ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, paging.getStartRow());
			pstmt.setInt(2, paging.getEndRow());
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Notice notice = new Notice();
				
				notice.setNoticeNo(rset.getInt("noticeno"));
				notice.setNoticeWriter(rset.getString("noticewriter"));
				notice.setNoticeTitle(rset.getString("noticetitle"));
				notice.setNoticeContent(rset.getString("noticecontent"));
				notice.setNoticeDate(rset.getDate("noticedate"));
				notice.setOriginalFilePath(rset.getString("original_filepath"));
				notice.setRenameFilePath(rset.getString("rename_filepath"));
				notice.setImportance(rset.getString("importance"));				
				notice.setImpEndDate(rset.getDate("imp_end_date"));
				notice.setReadCount(rset.getInt("readcount"));
				
				list.add(notice);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}
	
	//공지글번호로 한 개 조회 : 공지사항 상세보기용
	public Notice selectOne(Connection conn, int noticeNo) {
		Notice notice = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "select * from notice where noticeno = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, noticeNo);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				notice = new Notice();
				
				notice.setNoticeNo(rset.getInt("noticeno"));
				notice.setNoticeWriter(rset.getString("noticewriter"));
				notice.setNoticeTitle(rset.getString("noticetitle"));
				notice.setNoticeContent(rset.getString("noticecontent"));
				notice.setNoticeDate(rset.getDate("noticedate"));
				notice.setOriginalFilePath(rset.getString("original_filepath"));
				notice.setRenameFilePath(rset.getString("rename_filepath"));
				notice.setImportance(rset.getString("importance"));				
				notice.setImpEndDate(rset.getDate("imp_end_date"));
				notice.setReadCount(rset.getInt("readcount"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return notice;
	}
	
	//새 공지글 등록
	public int insertNotice(Connection conn, Notice notice) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "insert into notice values "
				+ "((select max(noticeno) + 1 from notice), "
				+ "?, sysdate, ?, ?, ?, ?, ?, ?, default)";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, notice.getNoticeTitle());
			pstmt.setString(2, notice.getNoticeWriter());
			pstmt.setString(3, notice.getNoticeContent());
			pstmt.setString(4, notice.getOriginalFilePath());
			pstmt.setString(5, notice.getRenameFilePath());
			pstmt.setString(6, notice.getImportance());
			pstmt.setDate(7, notice.getImpEndDate());
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	//공지글 수정
	public int updateNotice(Connection conn, Notice notice) {
		int result = 0;
		PreparedStatement pstmt = null;
		System.out.println("ndao : "+ notice);
		
		String query = null;
		if (notice.getOriginalFilePath() == null)  {			
			query = "update notice set "
					+ "noticetitle = ?, noticewriter = ?, "
					+ "noticecontent = ?, noticedate = sysdate, "	
					+ "original_filepath = null, rename_filepath = null, "
					+ "importance = ?, imp_end_date = ? "
					+ "where noticeno = ?";
		}else {			
			query = "update notice set "
					+ "noticetitle = ?, noticewriter = ?, "
					+ "noticecontent = ?, noticedate = sysdate, "
					+ "original_filepath = ?, rename_filepath = ?, "
					+ "importance = ?, imp_end_date = ? "
					+ "where noticeno = ?";
		}
		
		
		try {
			pstmt = conn.prepareStatement(query);			
			
			pstmt.setString(1, notice.getNoticeTitle());
			pstmt.setString(2, notice.getNoticeWriter());
			pstmt.setString(3, notice.getNoticeContent());
			
			if(notice.getOriginalFilePath() == null)  {				
				pstmt.setString(4, notice.getImportance());
				pstmt.setDate(5, notice.getImpEndDate());
				pstmt.setInt(6, notice.getNoticeNo());
			}else {
				pstmt.setString(4, notice.getOriginalFilePath());
				pstmt.setString(5, notice.getRenameFilePath());
				pstmt.setString(6, notice.getImportance());
				pstmt.setDate(7, notice.getImpEndDate());
				pstmt.setInt(8, notice.getNoticeNo());
			}
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	//공지글 삭제
	public int deleteNotice(Connection conn, int noticeNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "delete from notice where noticeno = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, noticeNo);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
	
	//새 공지글 3개를 리턴 : 작성날짜로 top-3 분석 이용함
	public ArrayList<Notice> selectNewTop3(Connection conn){
		ArrayList<Notice> list = new ArrayList<Notice>();
		Statement stmt = null;
		ResultSet rset = null;
		
		String query = "SELECT * " +
				"FROM (SELECT ROWNUM RNUM, NOTICENO, NOTICETITLE, NOTICEDATE "
				+ "FROM ( SELECT * FROM NOTICE "
				+ "        WHERE IMPORTANCE = 'N' "
				+ "        ORDER BY NOTICEDATE DESC)) "
				+ "WHERE RNUM BETWEEN 1 AND 3";
		
		try {
			stmt = conn.createStatement();
			rset = stmt.executeQuery(query);
			
			while(rset.next()) {
				Notice notice = new Notice();
				
				notice.setNoticeNo(rset.getInt("noticeno"));
				notice.setNoticeTitle(rset.getString("noticetitle"));
				notice.setNoticeDate(rset.getDate("noticedate"));
				
				list.add(notice);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			close(rset);
			close(stmt);
		}
		
		return list;
	}

	public int getListCount(Connection conn) {
		int listCount = 0;
		Statement stmt = null;
		ResultSet rset = null;
		
		String query = "select count(*) from notice";
		
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

	public int getSearchTitleCount(Connection conn, String keyword) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "select count(*) from notice where noticetitle like ?";
		
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

	public int getSearchContentCount(Connection conn, String keyword) {
		int listCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "select count(*) from notice where noticecontent like ?";
		
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
		
		String query = "select count(*) from notice where noticedate between ? and ?";
		
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

	public ArrayList<Notice> selectSearchTitle(Connection conn, String keyword, Paging paging) {
		ArrayList<Notice> list = new ArrayList<Notice>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "select * "
				+ "from (select rownum rnum, noticeno, noticetitle, noticedate, "
				+ "            noticewriter, noticecontent, original_filepath, rename_filepath, "
				+ "            importance, imp_end_date, readcount "
				+ "      from (select * from notice "
				+ "           where noticetitle like ? "
				+ "           order by importance desc, imp_seq asc, noticeno desc)"
				+ "     ) "
				+ "where rnum between ? and ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, paging.getStartRow());
			pstmt.setInt(3, paging.getEndRow());
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Notice notice = new Notice();
				
				notice.setNoticeNo(rset.getInt("noticeno"));
				notice.setNoticeWriter(rset.getString("noticewriter"));
				notice.setNoticeTitle(rset.getString("noticetitle"));
				notice.setNoticeContent(rset.getString("noticecontent"));
				notice.setNoticeDate(rset.getDate("noticedate"));
				notice.setOriginalFilePath(rset.getString("original_filepath"));
				notice.setRenameFilePath(rset.getString("rename_filepath"));
				notice.setImportance(rset.getString("importance"));				
				notice.setImpEndDate(rset.getDate("imp_end_date"));
				notice.setReadCount(rset.getInt("readcount"));
				
				list.add(notice);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public ArrayList<Notice> selectSearchContent(Connection conn, String keyword, Paging paging) {
		ArrayList<Notice> list = new ArrayList<Notice>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "select * "
				+ "from (select rownum rnum, noticeno, noticetitle, noticedate, "
				+ "            noticewriter, noticecontent, original_filepath, rename_filepath, "
				+ "            importance, imp_end_date, readcount "
				+ "      from (select * from notice "
				+ "           where noticecontent like ? "
				+ "           order by importance desc, imp_seq asc, noticeno desc)"
				+ "     ) "
				+ "where rnum between ? and ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, paging.getStartRow());
			pstmt.setInt(3, paging.getEndRow());
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Notice notice = new Notice();
				
				notice.setNoticeNo(rset.getInt("noticeno"));
				notice.setNoticeWriter(rset.getString("noticewriter"));
				notice.setNoticeTitle(rset.getString("noticetitle"));
				notice.setNoticeContent(rset.getString("noticecontent"));
				notice.setNoticeDate(rset.getDate("noticedate"));
				notice.setOriginalFilePath(rset.getString("original_filepath"));
				notice.setRenameFilePath(rset.getString("rename_filepath"));
				notice.setImportance(rset.getString("importance"));				
				notice.setImpEndDate(rset.getDate("imp_end_date"));
				notice.setReadCount(rset.getInt("readcount"));
				
				list.add(notice);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public ArrayList<Notice> selectSearchDate(Connection conn, Date begin, Date end, Paging paging) {
		ArrayList<Notice> list = new ArrayList<Notice>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "select * "
				+ "from (select rownum rnum, noticeno, noticetitle, noticedate, "
				+ "            noticewriter, noticecontent, original_filepath, rename_filepath, "
				+ "            importance, imp_end_date, readcount "
				+ "      from (select * from notice "
				+ "           where noticedate between ? and ? "
				+ "           order by importance desc, imp_seq asc, noticeno desc)"
				+ "     ) "
				+ "where rnum between ? and ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setDate(1, begin);
			pstmt.setDate(2, end);
			pstmt.setInt(3, paging.getStartRow());
			pstmt.setInt(4, paging.getEndRow());
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Notice notice = new Notice();
				
				notice.setNoticeNo(rset.getInt("noticeno"));
				notice.setNoticeWriter(rset.getString("noticewriter"));
				notice.setNoticeTitle(rset.getString("noticetitle"));
				notice.setNoticeContent(rset.getString("noticecontent"));
				notice.setNoticeDate(rset.getDate("noticedate"));
				notice.setOriginalFilePath(rset.getString("original_filepath"));
				notice.setRenameFilePath(rset.getString("rename_filepath"));
				notice.setImportance(rset.getString("importance"));				
				notice.setImpEndDate(rset.getDate("imp_end_date"));
				notice.setReadCount(rset.getInt("readcount"));
				
				list.add(notice);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int addReadCount(Connection conn, int noticeNo) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "update notice "
				+ "set readcount = readcount + 1 "
				+ "where noticeno = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, noticeNo);
			
			result = pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}
}







