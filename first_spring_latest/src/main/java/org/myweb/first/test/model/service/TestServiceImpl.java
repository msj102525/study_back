package org.myweb.first.test.model.service;

import org.myweb.first.test.model.dao.TestDao;
import org.myweb.first.test.model.vo.TestVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("testService")
public class TestServiceImpl implements TestService {

	@Autowired
	private TestDao testDao;
	
	@Override
	public int selectTestVO(TestVO vo) {
		return testDao.selectTestVO(vo);
	}

}
