package org.ict.first.member.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.ict.first.common.Paging;
import org.ict.first.common.Search;
import org.ict.first.common.SearchDate;
import org.ict.first.member.vo.Member;

@Mapper
public interface MemberMapper {
	//member-mapper.xml 의 element id 명과 메소드명이 반드시 같아야함
	public Member selectLogin(Member member); 
	public int insertMember(Member member); 
	public Member selectMember(String userid); 
	public int updateMember(Member member);	
	public int updateMember2(Member member);
	public int deleteMember(String userid); 
	public List<Member> selectList();	
	//페이징 처리 추가된 회원 목록 조회
	public List<Member> selectListP(Paging paging);	
	//회원 전체 목록 갯수 (관리자 제외)
	public int selectListCount(); 
	public int updateLoginOK(Member member); 
	public List<Member> selectSearchUserid(Search search); 
	public List<Member> selectSearchGender(Search search); 
	public List<Member> selectSearchAge(Search search); 
	public List<Member> selectSearchEnrollDate(Search search);
	public List<Member> selectSearchLoginOK(Search search); 
	public int selectCheckId(String userid); 
	//검색 목록 카운트 관련
	public int selectSearchIDCount(String keyword); 	
	public int selectSearchGenderCount(String keyword); 
	public int selectSearchAgeCount(int age); 	
	public int selectSearchDateCount(SearchDate searchDate); 	
	public int selectSearchLoginOKCount(String keyword); 
}
