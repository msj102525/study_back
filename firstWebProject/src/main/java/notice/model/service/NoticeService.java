package notice.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.getConnection;

import java.sql.Connection;
import java.util.ArrayList;

import notice.model.dao.NoticeDao;
import notice.model.dto.Notice;

public class NoticeService {
	private NoticeDao ndao = new NoticeDao();
	
	public ArrayList<Notice> selectTop3(){
		Connection conn = getConnection();
		ArrayList<Notice> list = ndao.selectTop3(conn);
		close(conn);
		return list;
	}
	
}
