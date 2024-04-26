package org.ict.first.member.service;

import java.util.ArrayList;
import java.util.List;

import org.ict.first.common.Paging;
import org.ict.first.common.Search;
import org.ict.first.common.SearchDate;
import org.ict.first.member.mapper.MemberMapper;
import org.ict.first.member.vo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("memberService")
@Transactional
public class MemberService {
	
	@Autowired  //자동 DI 처리됨 : 자동 객체 생성됨
	private MemberMapper memberMapper;	
		
	public Member selectLogin(Member member) {		
		return memberMapper.selectLogin(member);		
	}	
	
	public int insertMember(Member member) {		
		return memberMapper.insertMember(member);		
	}
	
	public Member selectMember(String userid) {		
		return memberMapper.selectMember(userid);		
	}
	
	public int updateMember(Member member) {		
		return memberMapper.updateMember(member);
	}	
	
	public int updateMember2(Member member) {		
		return memberMapper.updateMember2(member);
	}
	
	public int deleteMember(String userid) {		
		return memberMapper.deleteMember(userid);	
	}
	
	public ArrayList<Member> selectList() {		
		List<Member> list = memberMapper.selectList();
		return (ArrayList<Member>)list;
	}
	
	//페이징 처리된 목록 조회	
	public ArrayList<Member> selectList(Paging paging) {
		List<Member> list = memberMapper.selectListP(paging);
		return (ArrayList<Member>)list;
	}  	
	
	public int selectListCount() {
		return memberMapper.selectListCount();
	}
	
	public int updateLoginOK(Member member) {		
		return memberMapper.updateLoginOK(member);
	}
	
	public ArrayList<Member> selectSearchUserid(Search search) {		
		List<Member> list = memberMapper.selectSearchUserid(search);
		return (ArrayList<Member>)list;
	}
	
	public ArrayList<Member> selectSearchGender(Search search) {		
		List<Member> list = memberMapper.selectSearchGender(search);
		return (ArrayList<Member>)list;
	}
	
	public ArrayList<Member> selectSearchAge(Search search) {		
		List<Member> list = memberMapper.selectSearchAge(search);
		return (ArrayList<Member>)list;
	}
	
	public ArrayList<Member> selectSearchEnrollDate(Search search) {		
		List<Member> list = memberMapper.selectSearchEnrollDate(search);
		return (ArrayList<Member>)list;
	}
	
	public ArrayList<Member> selectSearchLoginOK(Search search) {		
		List<Member> list = memberMapper.selectSearchLoginOK(search);
		return (ArrayList<Member>)list;
	}
	
	public int selectCheckId(String userid) {		
		return memberMapper.selectCheckId(userid);
	}
	
	//검색 목록 카운트 관련	
	public int selectSearchIDCount(String keyword) {
		return memberMapper.selectSearchIDCount(keyword);
	}	
	
	public int selectSearchGenderCount(String keyword) {
		return memberMapper.selectSearchGenderCount(keyword);
	}	
	
	public int selectSearchAgeCount(int age) {
		return memberMapper.selectSearchAgeCount(age);
	}	
	
	public int selectSearchDateCount(SearchDate searchDate) {
		return memberMapper.selectSearchDateCount(searchDate);
	}	
	
	public int selectSearchLoginOKCount(String keyword) {
		return memberMapper.selectSearchLoginOKCount(keyword);
	}
}








