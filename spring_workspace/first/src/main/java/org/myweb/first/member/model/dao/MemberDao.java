package org.myweb.first.member.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.myweb.first.common.SearchDate;
import org.myweb.first.member.model.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("memberDao")
public class MemberDao {
	// 마이바티스 매퍼 파일에 쿼리문 별도로 작성함
	// root-context.xml 에 설정된 마이바티스 세션 객체를 연결 사용함
	@Autowired //root-context.xml 에서 생성한 객체를 자동 연결함
	private SqlSessionTemplate sqlSessionTemplate;

	public Member selectLogin(Member member) {
		return sqlSessionTemplate.selectOne("memberMapper.selectLogin", member);
	}

	public int insertMember(Member member) {
		return sqlSessionTemplate.insert("memberMapper.insertMember", member);
	}

	public Member selectMember(String userid) {
		return sqlSessionTemplate.selectOne("memberMapper.selectMember", userid);
	}

	public int updateMember(Member member) {
		return sqlSessionTemplate.update("memberMapper.updateMember", member);
	}

	public int deleteMember(String userid) {
		return sqlSessionTemplate.delete("memberMapper.deleteMember", userid);
	}

	public ArrayList<Member> selectList() {
		List<Member> list = sqlSessionTemplate.selectList("memberMapper.selectList");
		return (ArrayList<Member>)list;
	}

	public int updateLoginOK(Member member) {
		return sqlSessionTemplate.update("memberMapper.updateLoginOK", member);
	}

	public ArrayList<Member> selectSearchUserid(String keyword) {
		List<Member> list = sqlSessionTemplate.selectList("memberMapper.selectSearchUserid", keyword);
		return (ArrayList<Member>)list;
	}

	public ArrayList<Member> selectSearchGender(String keyword) {
		List<Member> list = sqlSessionTemplate.selectList("memberMapper.selectSearchGender", keyword);
		return (ArrayList<Member>)list;
	}

	public ArrayList<Member> selectSearchAge(int keyword) {
		List<Member> list = sqlSessionTemplate.selectList("memberMapper.selectSearchAge", keyword);
		return (ArrayList<Member>)list;
	}

	public ArrayList<Member> selectSearchEnrollDate(SearchDate searchDate) {
		List<Member> list = sqlSessionTemplate.selectList("memberMapper.selectSearchEnrollDate", searchDate);
		return (ArrayList<Member>)list;
	}

	public ArrayList<Member> selectSearchLoginOK(String keyword) {
		List<Member> list = sqlSessionTemplate.selectList("memberMapper.selectSearchLoginOK", keyword);
		return (ArrayList<Member>)list;
	}

	public int selectCheckId(String userid) {
		return sqlSessionTemplate.selectOne("memberMapper.selectCheckId", userid);
	}

}













