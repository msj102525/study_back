package member.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.getConnection;

import java.sql.Connection;

import member.model.dao.MemberDao;
import member.model.dto.User;

public class MemberService {
	private MemberDao mdao = new MemberDao(); // 의존성 주입 (DI : Dependency Injection)
	
	public User selectLogin(User user) {
		Connection conn = getConnection();
		User loginUser = mdao.selectLogin(conn, user);
		close(conn);
		return loginUser;
	}
	
}
