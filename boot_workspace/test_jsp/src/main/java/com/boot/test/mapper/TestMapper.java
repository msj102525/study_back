package com.boot.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.boot.test.dto.Board;

@Mapper
public interface TestMapper {  // mapper.xml 의 namespace 로 등록함
	List<Board> selectBoardList();  //매퍼 xml 의 select 앨리먼트의 id 명이 메소드명이어야 함
	public void insertBoard(Board board);
	public void updateBoard(Board board);
	public void deleteBoard(int boardNum);
	public Board selectBoard(int boardNum);
}
