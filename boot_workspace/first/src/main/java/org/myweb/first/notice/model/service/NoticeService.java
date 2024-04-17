package org.myweb.first.notice.model.service;

import java.util.ArrayList;

import org.myweb.first.common.Paging;
import org.myweb.first.common.Search;
import org.myweb.first.common.SearchDate;
import org.myweb.first.notice.model.vo.Notice;


public interface NoticeService {
	 ArrayList<Notice> selectList(Paging paging);	
	 Notice selectOne(int noticeNo);	
	 int insertNotice(Notice notice);	
	 int updateNotice(Notice notice);	
	 int deleteNotice(int noticeNo);	
	 ArrayList<Notice> selectTop3();
	 int selectListCount();
	 int selectSearchTitleCount(String keyword);
	 int selectSearchContentCount(String keyword);
	 int selectSearchDateCount(SearchDate date);
	 ArrayList<Notice> selectSearchTitle(Search search);
	 ArrayList<Notice> selectSearchTitle(String keyword);
	 ArrayList<Notice> selectSearchContent(Search search);
	 ArrayList<Notice> selectSearchDate(Search search);
	 void updateAddReadCount(int noticeNo);
	Notice selectLast();
}
