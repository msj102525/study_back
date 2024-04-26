package org.ict.first.notice.service;

import java.util.ArrayList;
import java.util.List;

import org.ict.first.common.Paging;
import org.ict.first.common.Search;
import org.ict.first.common.SearchDate;
import org.ict.first.notice.mapper.NoticeMapper;
import org.ict.first.notice.vo.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("noticeService")
@Transactional
public class NoticeService {
	@Autowired
	private NoticeMapper noticeMapper;
		
	public ArrayList<Notice> selectList(Paging paging){
		List<Notice> list = noticeMapper.selectList(paging);
		return (ArrayList<Notice>)list;
	}	
	
	public Notice selectOne(int noticeNo) {
		return noticeMapper.selectOne(noticeNo);
	}	
	
	public int insertNotice(Notice notice) {
		return noticeMapper.insertNotice(notice);
	}	
	
	public int updateNotice(Notice notice) {
		return noticeMapper.updateNotice(notice);
	}	
	
	public int deleteNotice(int noticeNo) {
		return noticeMapper.deleteNotice(noticeNo);
	}	
	
	public ArrayList<Notice> selectNewTop3(){
		List<Notice> list = noticeMapper.selectNewTop3();
		return (ArrayList<Notice>)list;
	}
	
	public int selectListCount() {		
		return noticeMapper.getListCount();
	}
	
	public int selectSearchTitleCount(String keyword) {
		return noticeMapper.getSearchTitleCount(keyword);
	}
	
	public int selectSearchContentCount(String keyword) {		
		return noticeMapper.getSearchContentCount(keyword);
	}
	
	public int selectSearchDateCount(SearchDate date) {		
		return noticeMapper.getSearchDateCount(date);
	}
	
	public ArrayList<Notice> selectSearchTitle(Search search) {
		List<Notice> list = noticeMapper.selectSearchTitle(search);
		return (ArrayList<Notice>)list;
	}	
	
	public ArrayList<Notice> selectSearchTitle(String keyword) {
		List<Notice> list = noticeMapper.selectSearchTitle(keyword);
		return (ArrayList<Notice>)list;
	}
	
	public ArrayList<Notice> selectSearchContent(Search search) {		
		List<Notice> list = noticeMapper.selectSearchContent(search);
		return (ArrayList<Notice>)list;
	}
	
	public ArrayList<Notice> selectSearchDate(Search search) {		
		List<Notice> list = noticeMapper.selectSearchDate(search);
		return (ArrayList<Notice>)list;
	}
	
	public int updateAddReadCount(int noticeNo) {		
		return noticeMapper.addReadCount(noticeNo);		
	}
	
	public Notice selectLast() {
		return noticeMapper.selectLast();
	}
}
