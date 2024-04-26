package org.ict.first.board.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.ict.first.board.vo.Board;
import org.ict.first.common.Paging;
import org.ict.first.common.Search;
import org.ict.first.common.SearchDate;

@Mapper
public interface BoardMapper {  //클래스명과 매퍼 xml 파일명이 같아야 함
	//간단한 쿼리문일 때는 어노테이션 방식이 편함
	@Select("select sysdate from dual")
	public String getTime();
	
	//복잡한 쿼리문일 때는 xml 방식이 필요함
	//주의 : 매퍼 xml 안의 각 element 의 id 명과 메소드명이 반드시 같아야 함
	public List<Board> selectTop3();	
	public int getListCount();
	public List<Board> selectList(Paging paging);
	public int insertOriginBoard(Board board);
	public int addReadCount(int boardNum);
	public Board selectBoard(int boardNum);
	public int updateReplySeq1(Board reply);
	public int updateReplySeq2(Board reply);
	public int insertReply1(Board reply);
	public int insertReply2(Board reply);
	public int deleteBoard(Board board);
	public int updateReply(Board reply);
	public int updateOrigin(Board board);
	public List<Board> selectSearchTitle(Search search);
	public List<Board> selectSearchWriter(Search search);
	public List<Board> selectSearchDate(Search search);
	public int getSearchTitleCount(String keyword);
	public int getSearchWriterCount(String keyword);
	public int getSearchDateCount(SearchDate date);
}
