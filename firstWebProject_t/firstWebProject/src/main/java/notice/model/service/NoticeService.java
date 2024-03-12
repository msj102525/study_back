package notice.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

import board.model.vo.Board;
import common.Paging;
import notice.model.dao.NoticeDao;
import notice.model.vo.Notice;

public class NoticeService {
	private NoticeDao ndao = new NoticeDao();
	
	public ArrayList<Notice> selectList(Paging paging){
		Connection conn = getConnection();
		ArrayList<Notice> list = ndao.selectList(conn, paging);
		close(conn);
		return list;
	}
	
	public Notice selectOne(int noticeNo) {
		Connection conn = getConnection();
		Notice notice = ndao.selectOne(conn, noticeNo);
		close(conn);
		return notice;
	}
	
	public int insertNotice(Notice notice) {
		Connection conn = getConnection();
		int result = ndao.insertNotice(conn, notice);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}		
		close(conn);
		return result;
	}
	
	public int updateNotice(Notice notice) {
		Connection conn = getConnection();
		int result = ndao.updateNotice(conn, notice);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}		
		close(conn);
		return result;
	}
	
	public int deleteNotice(int noticeNo) {
		Connection conn = getConnection();
		int result = ndao.deleteNotice(conn, noticeNo);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}		
		close(conn);
		return result;
	}
	
	public ArrayList<Notice> selectNewTop3(){
		Connection conn = getConnection();
		ArrayList<Notice> list = ndao.selectNewTop3(conn);
		close(conn);
		return list;
	}

	public int getListCount() {
		Connection conn = getConnection();
		int listCount = ndao.getListCount(conn);
		close(conn);
		return listCount;
	}

	public int getSearchTitleCount(String keyword) {
		Connection conn = getConnection();
		int listCount = ndao.getSearchTitleCount(conn, keyword);
		close(conn);
		return listCount;
	}

	public int getSearchContentCount(String keyword) {
		Connection conn = getConnection();
		int listCount = ndao.getSearchContentCount(conn, keyword);
		close(conn);
		return listCount;
	}

	public int getSearchDateCount(Date begin, Date end) {
		Connection conn = getConnection();
		int listCount = ndao.getSearchDateCount(conn, begin, end);
		close(conn);
		return listCount;
	}

	public ArrayList<Notice> selectSearchTitle(String keyword, Paging paging) {
		Connection conn = getConnection();
		ArrayList<Notice> list = ndao.selectSearchTitle(conn, keyword, paging);
		close(conn);
		return list;
	}

	public ArrayList<Notice> selectSearchContent(String keyword, Paging paging) {
		Connection conn = getConnection();
		ArrayList<Notice> list = ndao.selectSearchContent(conn, keyword, paging);
		close(conn);
		return list;
	}

	public ArrayList<Notice> selectSearchDate(Date begin, Date end, Paging paging) {
		Connection conn = getConnection();
		ArrayList<Notice> list = ndao.selectSearchDate(conn, begin, end, paging);
		close(conn);
		return list;
	}

	public void addReadCount(int noticeNo) {
		Connection conn = getConnection();
		int result = ndao.addReadCount(conn, noticeNo);
		if(result > 0) {
			commit(conn);
		}else {
			rollback(conn);
		}		
		close(conn);		
	}
}
