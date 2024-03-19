package org.myweb.first.board.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.myweb.first.board.model.service.BoardService;
import org.myweb.first.board.model.vo.Board;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BoardController {
	// 이 클래스에서 메소드 안의 요청과 반환값달의 결과 출력 확인을 위한 로그 객체 생성
	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	private BoardService boardService;
	
	// 요청 처리용 메소드 작성부 -----------------------------------------------------
	@RequestMapping(value="btop3.do", method=RequestMethod.POST)
	@ResponseBody
	public String boardTop3Method() throws UnsupportedEncodingException {
		// ajax 요청시 리턴방법은 여러가지가 있음 
		// response 객체 이용시에는 2가지 중 선택 사용하면 됨
		// 1. 출력스트림으로 응답하는 방법
		// 2. 뷰리졸버 리턴하는 방법 : response body 에 내보낼 값을 저장함
		//			=> sevlet-context.xml 에 설정된 JSONView 클래스가 받아서 내보냄 : 등록 설정 필요
		
		// 조회수 많은 순으로 인기 게시글 3개 조회해 옴
		ArrayList<Board> list = boardService.selectTop3();
		
		// list 를 json 배열에 옮기기
		JSONArray jarr = new JSONArray();
		
		for(Board board : list) {
			// board 객체 저장용 json 객체 생성
			JSONObject job = new JSONObject();
			
			job.put("bnum", board.getBoardNum());
			// 한굴에 대해서는 인코딩해서 json에 담음 (한글 깨짐 방지)
			job.put("btitle", URLEncoder.encode(board.getBoardTitle(), "UTF-8") );
			job.put("rcount", board.getBoardReadCount());
			
			// job 를 jarr 에 추가
			jarr.add(job);
		}
		
		// 전송용 json 객체 준비
		JSONObject sendJson = new JSONObject();
		// 전송용 객체에 jarr 을 담음
		sendJson.put("list", jarr);
		
		// 전송용 json 을 json stirng 으로 바꿔서 전송되게 함
		return sendJson.toJSONString();
	}
	
}














