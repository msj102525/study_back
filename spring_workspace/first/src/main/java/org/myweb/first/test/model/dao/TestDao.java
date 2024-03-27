package org.myweb.first.test.model.dao;

import org.mybatis.spring.SqlSessionTemplate;
import org.myweb.first.test.controller.TestController;
import org.myweb.first.test.model.vo.TestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository("testDao")
public class TestDao {
	private static Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	public int selectTestVO(TestVO vo) {
		
		return sqlSessionTemplate.selectOne("testMapper.selectTestVO", vo);
	}
	
}
