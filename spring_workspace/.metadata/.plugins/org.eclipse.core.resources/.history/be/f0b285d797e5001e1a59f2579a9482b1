package org.myweb.first.board.model.dao;

import java.util.ArrayList;
import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.myweb.first.board.model.vo.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("boardDao")
public class BoardDao {
	// 마이바티스 매퍼파일에 쿼리문 별도로 작성함
	// root-context.xml 에 저장된 마이바티스 연결 객체를 사용해서, 매퍼의 쿼리문을 사용 실행 처리함
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	// 조회수 많은 인기 게시글 3개 Top-3 조회
	public ArrayList<Board> selectTop3(){
		List<Board> list = sqlSessionTemplate.selectList("boardMapper.selectTop3");
		return (ArrayList<Board>)list;
		
	}
	
}
