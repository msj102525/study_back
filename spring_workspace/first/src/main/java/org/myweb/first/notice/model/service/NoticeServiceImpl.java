package org.myweb.first.notice.model.service;

import java.util.ArrayList;

import org.myweb.first.common.Paging;
import org.myweb.first.common.Search;
import org.myweb.first.common.SearchDate;
import org.myweb.first.notice.model.dao.NoticeDao;
import org.myweb.first.notice.model.vo.Notice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("noticeService")
public class NoticeServiceImpl implements NoticeService {
	@Autowired
	private NoticeDao noticeDao;
	
	@Override
	public ArrayList<Notice> selectList(Paging paging){
		return noticeDao.selectList(paging);
	}
	
	@Override
	public Notice selectOne(int noticeNo) {
		return noticeDao.selectOne(noticeNo);
	}
	
	@Override
	public int insertNotice(Notice notice) {
		return noticeDao.insertNotice(notice);
	}
	
	@Override
	public int updateNotice(Notice notice) {
		return noticeDao.updateNotice(notice);
	}
	
	@Override
	public int deleteNotice(int noticeNo) {
		return noticeDao.deleteNotice(noticeNo);
	}
	
	@Override
	public ArrayList<Notice> selectTop3(){
		return noticeDao.selectTop3();
	}

	@Override
	public int selectListCount() {		
		return noticeDao.getListCount();
	}

	@Override
	public int selectSearchTitleCount(String keyword) {
		return noticeDao.getSearchTitleCount(keyword);
	}

	@Override
	public int selectSearchContentCount(String keyword) {		
		return noticeDao.getSearchContentCount(keyword);
	}

	@Override
	public int selectSearchDateCount(SearchDate date) {		
		return noticeDao.getSearchDateCount(date);
	}

	@Override
	public ArrayList<Notice> selectSearchTitle(Search search) {
		return noticeDao.selectSearchTitle(search);
	}
	
	@Override
	public ArrayList<Notice> selectSearchTitle(String keyword) {
		return noticeDao.selectSearchTitle(keyword);
	}

	@Override
	public ArrayList<Notice> selectSearchContent(Search search) {		
		return noticeDao.selectSearchContent(search);
	}

	@Override
	public ArrayList<Notice> selectSearchDate(Search search) {		
		return noticeDao.selectSearchDate(search);
	}

	@Override
	public void updateAddReadCount(int noticeNo) {		
		noticeDao.addReadCount(noticeNo);		
	}

	@Override
	public Notice selectLast() {
		return noticeDao.selectLast();
	}
}
