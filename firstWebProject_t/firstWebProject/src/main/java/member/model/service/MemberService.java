package member.model.service;

import static common.JDBCTemplate.*;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;

import member.model.dao.MemberDao;
import member.model.vo.Member;

public class MemberService {
	//DI(Dependency Injection : 의존성 주입)
	private MemberDao mdao = new MemberDao();
	
	public MemberService() {}
	
	public Member selectLogin(String userid, String userpwd) {
		Connection conn = getConnection();
		Member loginMember = mdao.selectLogin(conn, userid, userpwd);
		close(conn);
		return loginMember;
	}
	
	public int insertMember(Member member) {
		Connection conn = getConnection();
		int result = mdao.insertMember(conn, member);
		if(result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		return result;
	}

	public Member selectMember(String userid) {
		Connection conn = getConnection();
		Member member = mdao.selectMember(conn, userid);
		close(conn);
		return member;
	}

	public int updateMember(Member member) {
		Connection conn = getConnection();
		int result = mdao.updateMember(conn, member);
		if(result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		return result;
	}

	public int deleteMember(String userid) {
		Connection conn = getConnection();
		int result = mdao.deleteMember(conn, userid);
		if(result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		return result;
	}

	public ArrayList<Member> selectList() {
		Connection conn = getConnection();
		ArrayList<Member> list = mdao.selectList(conn);
		close(conn);
		return list;
	}

	public int updateLoginOK(String userid, String loginok) {
		Connection conn = getConnection();
		int result = mdao.updateLoginOK(conn, userid, loginok);
		if(result > 0)
			commit(conn);
		else
			rollback(conn);
		close(conn);
		return result;
	}

	public ArrayList<Member> selectSearchUserid(String keyword) {
		Connection conn = getConnection();
		ArrayList<Member> list = mdao.selectSearchUserid(conn, keyword);
		close(conn);
		return list;
	}

	public ArrayList<Member> selectSearchGender(String keyword) {
		Connection conn = getConnection();
		ArrayList<Member> list = mdao.selectSearchGender(conn, keyword);
		close(conn);
		return list;
	}

	public ArrayList<Member> selectSearchAge(int keyword) {
		Connection conn = getConnection();
		ArrayList<Member> list = mdao.selectSearchAge(conn, keyword);
		close(conn);
		return list;
	}

	public ArrayList<Member> selectSearchEnrollDate(Date begin, Date end) {
		Connection conn = getConnection();
		ArrayList<Member> list = mdao.selectSearchEnrollDate(conn, begin, end);
		close(conn);
		return list;
	}

	public ArrayList<Member> selectSearchLoginOK(String keyword) {
		Connection conn = getConnection();
		ArrayList<Member> list = mdao.selectSearchLoginOK(conn, keyword);
		close(conn);
		return list;
	}

	public int selectCheckId(String userid) {
		Connection conn = getConnection();
		int idCount = mdao.selectCheckId(conn, userid);
		close(conn);
		return idCount;
	}
}








