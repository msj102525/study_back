package org.myweb.first.member.model.dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import member.model.vo.Member;
import static common.JDBCTemplate.close;

public class MemberDao {
	public MemberDao() {}

	public Member selectLogin(Connection conn, String userid, String userpwd) {
		Member member = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "select * from member where userid = ? and userpwd = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, userid);
			pstmt.setString(2, userpwd);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				member = new Member();
				
				member.setUserId(userid);
				member.setUserPwd(userpwd);
				member.setUserName(rset.getString("username"));
				member.setGender(rset.getString("gender"));
				member.setAge(rset.getInt("age"));
				member.setPhone(rset.getString("phone"));
				member.setEmail(rset.getString("email"));
				member.setEnrollDate(rset.getDate("enroll_date"));
				member.setLastModified(rset.getDate("lastmodified"));
				member.setSignType(rset.getString("signtype"));
				member.setAdminYN(rset.getString("admin_yn"));
				member.setLoginOk(rset.getString("login_ok"));	
				member.setPhotoFileName(rset.getString("photo_filename"));
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return member;
	}

	public int insertMember(Connection conn, Member member) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "insert into member values "
				+ "(?, ?, ?, ?, ?, ?, ?, default, default, default, default, default, ?)";
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, member.getUserId());
			pstmt.setString(2, member.getUserPwd());
			pstmt.setString(3, member.getUserName());
			pstmt.setString(4, member.getGender());
			pstmt.setInt(5, member.getAge());
			pstmt.setString(6, member.getPhone());
			pstmt.setString(7, member.getEmail());
			pstmt.setString(8, member.getPhotoFileName());
			
			result = pstmt.executeUpdate();			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public Member selectMember(Connection conn, String userid) {
		Member member = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "select * from member where userid = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, userid);			
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				member = new Member();
				
				//결과매핑 : 컬럼값 꺼내서 필드에 옮기기
				member.setUserId(userid);
				member.setUserPwd(rset.getString("userpwd"));
				member.setUserName(rset.getString("username"));
				member.setGender(rset.getString("gender"));
				member.setAge(rset.getInt("age"));
				member.setPhone(rset.getString("phone"));
				member.setEmail(rset.getString("email"));
				member.setEnrollDate(rset.getDate("enroll_date"));
				member.setLastModified(rset.getDate("lastmodified"));
				member.setSignType(rset.getString("signtype"));
				member.setAdminYN(rset.getString("admin_yn"));
				member.setLoginOk(rset.getString("login_ok"));	
				member.setPhotoFileName(rset.getString("photo_filename"));				
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return member;
	}

	public int updateMember(Connection conn, Member member) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "update member set age = ?, phone = ?, email = ?, "
				+ "photo_filename = ?, lastmodified = default where userid = ?";
		
		try {
			pstmt = conn.prepareStatement(query);			
			
			pstmt.setInt(1, member.getAge());
			pstmt.setString(2, member.getPhone());
			pstmt.setString(3, member.getEmail());
			pstmt.setString(4, member.getPhotoFileName());
			pstmt.setString(5, member.getUserId());
			
			result = pstmt.executeUpdate();			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public int deleteMember(Connection conn, String userid) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "delete from member where userid = ?";
		
		try {
			pstmt = conn.prepareStatement(query);				
			
			pstmt.setString(1, userid);
			
			result = pstmt.executeUpdate();			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public ArrayList<Member> selectList(Connection conn) {
		ArrayList<Member> list = new ArrayList<Member>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//관리자를 제외한 일반회원만 전체 조회
		String query = "select * from member where admin = 'N'";
		
		try {
			pstmt = conn.prepareStatement(query);					
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Member member = new Member();
				
				//결과매핑 : 컬럼값 꺼내서 필드에 옮기기
				member.setUserId(rset.getString("userid"));
				member.setUserPwd(rset.getString("userpwd"));
				member.setUserName(rset.getString("username"));
				member.setGender(rset.getString("gender"));
				member.setAge(rset.getInt("age"));
				member.setPhone(rset.getString("phone"));
				member.setEmail(rset.getString("email"));
				member.setEnrollDate(rset.getDate("enroll_date"));
				member.setLastModified(rset.getDate("lastmodified"));
				member.setSignType(rset.getString("signtype"));
				member.setAdminYN(rset.getString("admin_yn"));
				member.setLoginOk(rset.getString("login_ok"));	
				member.setPhotoFileName(rset.getString("photo_filename"));
				
				list.add(member);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int updateLoginOK(Connection conn, String userid, String loginok) {
		int result = 0;
		PreparedStatement pstmt = null;
		
		String query = "update member set login_ok = ? where userid = ?";
		
		try {
			pstmt = conn.prepareStatement(query);				
			
			pstmt.setString(1, loginok);
			pstmt.setString(2, userid);
			
			result = pstmt.executeUpdate();			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}
		
		return result;
	}

	public ArrayList<Member> selectSearchUserid(Connection conn, String keyword) {
		ArrayList<Member> list = new ArrayList<Member>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		//검색할 userid 에 대해 키워드 값이 유사한 형태의 값들을 선택
		String query = "select * from member where admin = 'N' and userid like ?";  //'%'||?||'%' : 에러
		
		try {
			pstmt = conn.prepareStatement(query);	
			pstmt.setString(1, "%" + keyword + "%");
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Member member = new Member();
				
				//결과매핑 : 컬럼값 꺼내서 필드에 옮기기
				member.setUserId(rset.getString("userid"));
				member.setUserPwd(rset.getString("userpwd"));
				member.setUserName(rset.getString("username"));
				member.setGender(rset.getString("gender"));
				member.setAge(rset.getInt("age"));
				member.setPhone(rset.getString("phone"));
				member.setEmail(rset.getString("email"));
				member.setEnrollDate(rset.getDate("enroll_date"));
				member.setLastModified(rset.getDate("lastmodified"));
				member.setSignType(rset.getString("signtype"));
				member.setAdminYN(rset.getString("admin_yn"));
				member.setLoginOk(rset.getString("login_ok"));	
				member.setPhotoFileName(rset.getString("photo_filename"));	
				
				list.add(member);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public ArrayList<Member> selectSearchGender(Connection conn, String keyword) {
		ArrayList<Member> list = new ArrayList<Member>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;		
		
		String query = "select * from member where admin = 'N' and gender = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, keyword);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Member member = new Member();
				
				//결과매핑 : 컬럼값 꺼내서 필드에 옮기기
				member.setUserId(rset.getString("userid"));
				member.setUserPwd(rset.getString("userpwd"));
				member.setUserName(rset.getString("username"));
				member.setGender(rset.getString("gender"));
				member.setAge(rset.getInt("age"));
				member.setPhone(rset.getString("phone"));
				member.setEmail(rset.getString("email"));
				member.setEnrollDate(rset.getDate("enroll_date"));
				member.setLastModified(rset.getDate("lastmodified"));
				member.setSignType(rset.getString("signtype"));
				member.setAdminYN(rset.getString("admin_yn"));
				member.setLoginOk(rset.getString("login_ok"));	
				member.setPhotoFileName(rset.getString("photo_filename"));	
				
				list.add(member);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public ArrayList<Member> selectSearchAge(Connection conn, int keyword) {
		ArrayList<Member> list = new ArrayList<Member>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;		
		
		String query = "select * from member where admin = 'N' and ";
		
		if(keyword == 60) {
			query += " age >= ?";
		}else {
			query += " age between ? and ?";
		}
		
		try {
			pstmt = conn.prepareStatement(query);	
			
			if(keyword == 60) {
				pstmt.setInt(1, keyword);
			}else {
				pstmt.setInt(1, keyword);
				pstmt.setInt(2, keyword + 9);
			}
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Member member = new Member();
				
				//결과매핑 : 컬럼값 꺼내서 필드에 옮기기
				member.setUserId(rset.getString("userid"));
				member.setUserPwd(rset.getString("userpwd"));
				member.setUserName(rset.getString("username"));
				member.setGender(rset.getString("gender"));
				member.setAge(rset.getInt("age"));
				member.setPhone(rset.getString("phone"));
				member.setEmail(rset.getString("email"));
				member.setEnrollDate(rset.getDate("enroll_date"));
				member.setLastModified(rset.getDate("lastmodified"));
				member.setSignType(rset.getString("signtype"));
				member.setAdminYN(rset.getString("admin_yn"));
				member.setLoginOk(rset.getString("login_ok"));	
				member.setPhotoFileName(rset.getString("photo_filename"));
				
				list.add(member);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public ArrayList<Member> selectSearchEnrollDate(Connection conn, Date begin, Date end) {
		ArrayList<Member> list = new ArrayList<Member>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;		
		
		String query = "select * from member where admin = 'N' and enroll_date between ? and ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setDate(1, begin);
			pstmt.setDate(2, end);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Member member = new Member();
				
				//결과매핑 : 컬럼값 꺼내서 필드에 옮기기
				member.setUserId(rset.getString("userid"));
				member.setUserPwd(rset.getString("userpwd"));
				member.setUserName(rset.getString("username"));
				member.setGender(rset.getString("gender"));
				member.setAge(rset.getInt("age"));
				member.setPhone(rset.getString("phone"));
				member.setEmail(rset.getString("email"));
				member.setEnrollDate(rset.getDate("enroll_date"));
				member.setLastModified(rset.getDate("lastmodified"));
				member.setSignType(rset.getString("signtype"));
				member.setAdminYN(rset.getString("admin_yn"));
				member.setLoginOk(rset.getString("login_ok"));	
				member.setPhotoFileName(rset.getString("photo_filename"));
				
				list.add(member);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public ArrayList<Member> selectSearchLoginOK(Connection conn, String keyword) {
		ArrayList<Member> list = new ArrayList<Member>();
		PreparedStatement pstmt = null;
		ResultSet rset = null;		
		
		String query = "select * from member where admin = 'N' and login_ok = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, keyword);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				Member member = new Member();
				
				//결과매핑 : 컬럼값 꺼내서 필드에 옮기기
				member.setUserId(rset.getString("userid"));
				member.setUserPwd(rset.getString("userpwd"));
				member.setUserName(rset.getString("username"));
				member.setGender(rset.getString("gender"));
				member.setAge(rset.getInt("age"));
				member.setPhone(rset.getString("phone"));
				member.setEmail(rset.getString("email"));
				member.setEnrollDate(rset.getDate("enroll_date"));
				member.setLastModified(rset.getDate("lastmodified"));
				member.setSignType(rset.getString("signtype"));
				member.setAdminYN(rset.getString("admin_yn"));
				member.setLoginOk(rset.getString("login_ok"));	
				member.setPhotoFileName(rset.getString("photo_filename"));	
				
				list.add(member);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return list;
	}

	public int selectCheckId(Connection conn, String userid) {
		int idCount = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		
		String query = "select count(userid) from member "
				+ "where userid = ?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, userid);
			
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				idCount = rset.getInt(1); //select 절의 항목 순번으로도 값 추출할 수 있음
				System.out.println("mdao - idCount : " + idCount);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		
		return idCount;
	}

}













