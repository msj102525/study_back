package com.boot.test.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.boot.test.dto.Board;

@Mapper
public interface TestMapper {
	List<Board> selectBoardList();
}
