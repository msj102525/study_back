package org.ict.first.notice.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.ict.first.common.Paging;
import org.ict.first.common.Search;
import org.ict.first.common.SearchDate;
import org.ict.first.notice.vo.Notice;

@Mapper
public interface NoticeMapper {
	//notice-mapper.xml 의 element id 명과 메소드명이 반드시 일치해야 함
	
	// 공지사항 페이지 단위로 목록 조회 : 공지사항 목록보기용
	public List<Notice> selectList(Paging paging);
	// 공지글번호로 한 개 조회 : 공지사항 상세보기용
	public Notice selectOne(int noticeNo); 
	// 새 공지글 등록
	public int insertNotice(Notice notice); 
	// 공지글 수정
	public int updateNotice(Notice notice);
	// 공지글 삭제
	public int deleteNotice(int noticeNo); 
	// 새 공지글 3개를 리턴 : 작성날짜로 top-3 분석 이용함
	public List<Notice> selectNewTop3();
	public int getListCount(); 
	public int getSearchTitleCount(String keyword); 
	public int getSearchContentCount(String keyword);
	public int getSearchDateCount(SearchDate date); 
	public List<Notice> selectSearchTitle(Search search);
	public List<Notice> selectSearchTitle(String keyword); 
	public List<Notice> selectSearchContent(Search search); 
	public List<Notice> selectSearchDate(Search search); 
	public int addReadCount(int noticeNo); 
	public Notice selectLast();
}
