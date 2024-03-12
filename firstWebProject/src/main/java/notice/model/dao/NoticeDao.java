package notice.model.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import notice.model.dto.Notice;
import static common.JDBCTemplate.*;

public class NoticeDao {
	
	// 새 공지글 3개 리턴 : 작성날짜로 top-3 분석 이용함
	public ArrayList<Notice> selectTop3(Connection conn) {
		ArrayList<Notice> list = new ArrayList<Notice>();
		Statement stmt =null;
		ResultSet rset = null;
		
		String query = "SELECT * "
				+ "FROM (SELECT ROWNUM rnum, noticeno, noticetitle, noticedate "
				+ "FROM (SELECT * FROM NOTICE "
				+ "WHERE importance = 'N' "
				+ "ORDER By noticedate)) "
				+ "WHERE rnum BETWEEN 1 AND 3";
		
		try {
			stmt = conn.createStatement();
			
			rset = stmt.executeQuery(query);
			
			// 결과매핑
			while(rset.next()) {
				Notice notice = new Notice();
				
				notice.setNoticeNo(rset.getInt("noticeNo"));
				notice.setNoticeTitle(rset.getString("noticeTitle"));
				notice.setNoticeDate(rset.getDate("noticeDate"));
				
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
}
