package org.myweb.first.test.controller;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.myweb.first.notice.model.service.NoticeService;
import org.myweb.first.notice.model.vo.Notice;
import org.myweb.first.test.model.service.TestService;
import org.myweb.first.test.model.vo.TestVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class TestController {
	// 이 컨트롤러 메소드들이 잘 구동되는지, 값이 잘 전달 또는 리턴되었는지 확인하기 위한 로그 객체 생성
	private static final Logger logger = LoggerFactory.getLogger(TestController.class);
	
	@Autowired
	private TestService testService;
	
	@Autowired
	private NoticeService noticeService;
	
	// 뷰 페이지 이동 처리용 --------------------------------------
	
	@RequestMapping("moveAjax.do")
	public String moveAjaxPage() {
		return "test/testAjaxView";
	}
	
	// *************************************************
	// ajax 요청 처리용 메소드 작성부 -----------------------------------
	// Asynchronous Javascript And Xml
	// 자바스크립트와 xml 을 이용한 비동기식 네트워크 방식 : 별도의 입출력 스트림을 가짐
	@RequestMapping("test1.do")
	// @ResponseBody  // 문자값 하나 리턴시에는 생략해도 됨
	public void test1Method(HttpServletResponse response) throws IOException {
		// 서비스 메소드 호출하고 결과받기 : 생략
		
				// 클라이언트로 내보낼 출력스트림 생성
				// 문자값 전송시에는 mimiType 설정 생략해도 됨
				// response.setContentType("text/html; charset=utf-8");
				PrintWriter out = response.getWriter();
				
				out.append("served at : /first");
				out.flush();
				out.close();
	}
	
	@RequestMapping(value="test2.do", method=RequestMethod.POST	)
	// @ResponseBody // 문자열 하나 리턴시에는 생략해도 됨
	public void test2Method(TestVO vo, HttpServletResponse response) throws IOException {
		// 서비스 메소드 호출하고 결과받기 : 생략
		logger.info(vo.toString());
		int result = testService.selectTestVO(vo);
		
		// 클라이언트로 내보낼 출력스트림 생성
		// 문자값 전송시에는 mimiType 설정 생략해도 됨
		// response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		
		if(result == 1) {
			out.append("ok");
		} else {
			out.append("no member");
		}
		out.flush();
		out.close();
	}
	
	@RequestMapping(value="test3.do", method=RequestMethod.POST)
	@ResponseBody // 리턴하는 jsonString을 response body에 담아서 보낸다는 의미임
	public String test3Method(HttpServletResponse response) throws IOException {
		// 클라이언트로부터 요청이 오면, jsonString 을 별도의 스트림을 열어서 보낼수도 있고,
		// 또는 뷰리졸버로 리턴하면 등록된 jsonView 가 받아서 내보낼 수도 있음
		
		// Service 메소드 호출하고 결과받기 
		// 최근 등록한 공지글 1개 조회해 옴
		Notice notice = noticeService.selectLast();
		logger.info(notice.toString());
		
		// 받은 notice 를 JSONOBJECT 에 옮겨 담기 처리
		JSONObject job = new JSONObject();
		
		job.put("noticeno", notice.getNoticeNo());
		// 문자열값에 한글이 있다면, 반드시 인코딩해서 저장해야 함
		job.put("noticetitle", URLEncoder.encode(notice.getNoticeTitle(), "utf-8"));
		job.put("noicewriter", notice.getNoticeWriter());
		// 날짜데이터는 반드시 문자열로 바꿔서 저장할 것 : 날짜 그대로 저장하면 뷰에서 json 전체 출력안됨
		job.put("noticedate", notice.getNoticeDate().toString());
		// 문자열값에 한글이 있다면, 반드시 인코딩해서 저장해야 함
		job.put("noticecontent", URLEncoder.encode(notice.getNoticeContent(), "utf-8"));
		
		// ajax 로 서비스 요청시 클라이언트로 응답하는 방법은 2가지
		// 방법 1 : 
		// public void 형 => 직접 출력스트림을 만들어서 내보냄
		// 방법 2 :
		// public String 형 => 설정된 JsonView 로 내보냄
		
		// 리턴되는 json 객체의 string 문저열을 등록된 뷰리졸버인 JsonView로 보냄
		// servlet-context.xml 의 JsonView 가 받아서 내보냄
		return job.toJSONString();
	}
	
	@RequestMapping(value="test4.do", method=RequestMethod.POST)
	@ResponseBody
	public String test4Method(
				@RequestParam("keyword") String keyword,
				HttpServletResponse response) throws UnsupportedEncodingException {
		// 전달받은 키워드로 공지사항 제목 검색 메소드를 실행하고 결과받기
		ArrayList<Notice> list = noticeService.selectSearchTitle(keyword);
		
		// response 에 내보낼 값에 대한 mimiType 설정
		response.setContentType("application/json; charset=utf-8");
		
		// 리턴된 list 를 json 배열에 옮겨 담기
		JSONArray jarr = new JSONArray();
		
		for(Notice notice : list) {
			// notice 값들을 저장할 json 객체 생성
			JSONObject job = new JSONObject();
			job.put("notice", notice.getNoticeNo());
			// 저장할 문자열에 한글이 있다면 반드시 인코딩해서 저장해야 함
			job.put("noticetitle", URLEncoder.encode(notice.getNoticeTitle(), "utf-8"));
			job.put("noticewriter", notice.getNoticeWriter());
			// 날짜는 반드시 문자열로 바꿔서 저장할 것
			job.put("noticedate", notice.getNoticeDate().toString());
			
			jarr.add(job);
		} // for
		
		// 전송용 json 객체 생성
		JSONObject sendJson = new JSONObject();
		// 전송용 json 에 jarr 을 저장함
		sendJson.put("list", jarr);
		
		return sendJson.toJSONString();
	}
	
	// 클라이언트가 보낸 json 객체를 받아서 처리하는 컨트롤러 메소드
	// get : 전송값이 request 의 head 에 기록되어서 전송옴 (url 에 보여짐)
	// post : 전송값이 request 의 body 에 인코딩되어서 기록되어 전송옴 (url 에 안 보여짐, 인코딩 처리필요함)
	@RequestMapping(value="test5.do", method = RequestMethod.POST)
	public ResponseEntity<String> test5Method(
				@RequestBody String param) throws ParseException {
		// post 로 request body 에 기록된 json 문자열을 꺼내서 param 변수에 저장하라는 의미임
		
		// param 에 저장된 json 문자열을 json 객체로 바꿈
		JSONParser jparser = new JSONParser();
		JSONObject job = (JSONObject)jparser.parse(param);
		
		// json 객체가 가진 각 필드(property) 값을 추출해서 vo 객체(Notice)에 저장
		Notice notice = new Notice();
		notice.setNoticeTitle((String)job.get("title"));
		notice.setNoticeWriter((String)job.get("writer"));
		notice.setNoticeContent((String)job.get("content"));
		
		// 새 공지글 등록 처리용 메소드 실행
		int result = noticeService.insertNotice(notice);
		
		// ResponseEntity<T> : 클라이언트에게 응답하는 용도의 객체임
		// 뷰리졸버가 아닌 출력스트림으로 나감
		if(result > 0) {
			return new ResponseEntity<String>("sucess", HttpStatus.OK);
		} else {
			return new ResponseEntity<String>("failed", HttpStatus.REQUEST_TIMEOUT);
		}
		
	}
	
	// 클라이언트가 보낸 json 배열을 받아서 처리하는 컨트롤러 메소드
		// get : 전송값이 request 의 head 에 기록되어서 전송옴 (url 에 보여짐)
		// post : 전송값이 request 의 body 에 인코딩되어서 기록되어 전송옴 (url 에 안 보여짐, 인코딩 처리필요함)
	@RequestMapping(value = "test6.do", method = RequestMethod.POST)
	public ResponseEntity<String> test6Method(@RequestBody String param) throws ParseException {
		// post 로 request body 에 기록된 json 문자열을 꺼내서 param 변수에 저장하라는 의미임

		logger.info(param);
		
		// param 에 저장된 json 문자열을 json 객체로 바꿈
		JSONParser jparser = new JSONParser();
		JSONArray jarr = (JSONArray) jparser.parse(param);
		
		
		
		// jarr 이 가진
		// json 객체가 가진 각 필드(property) 값을 추출해서 vo 객체(Notice)에 저장
		for (int i = 0; i < jarr.size(); i++) {
			JSONObject job = (JSONObject)jarr.get(i);
			logger.info(job.toString());
			Notice notice = new Notice();
			notice.setNoticeTitle((String) job.get("title"));
			notice.setNoticeWriter((String) job.get("writer"));
			notice.setNoticeContent((String) job.get("content"));

			// 새 공지글 등록 처리용 메소드 실행
			int result = noticeService.insertNotice(notice);

			// 에러 발생 또는 공지글 등록 실패시
			if (result <= 0) {
				return new ResponseEntity<String>("failed", HttpStatus.REQUEST_TIMEOUT);
			}
		}
		// ResponseEntity<T> : 클라이언트에게 응답하는 용도의 객체임
		// 뷰리졸버가 아닌 출력스트림으로 나감
		return new ResponseEntity<String>("sucess", HttpStatus.OK);

	}
	
	@RequestMapping(value="testFileUp.do", method=RequestMethod.POST)
	public void testFileUploadMethod(
				HttpServletRequest request, HttpServletResponse response,
				@RequestParam("message") String message, // comand 객체 사용해도 됨
				@RequestParam(name="upfile", required=false) MultipartFile mfile) throws IllegalStateException, IOException {
		logger.info("message + " + message.toString());
		logger.info("mfile : " + mfile.getOriginalFilename());
		
		// 업로드된 파일 저장 폴더 지정
		String savePath = request.getSession().getServletContext().getRealPath(
				"resources/test_upfiles");
		
		// 업로드된 파일을 File 객체 만들어서, 저장 폴더에 저장 처리
		mfile.transferTo(new File(savePath + "\\" + mfile.getOriginalFilename()));
		
		// 응답 문자 보내기
		response.getWriter().append("ok").flush();
		
	}
	
	// ajax 요청으로 파일 다운로드 처리용
	@RequestMapping("filedown.do")
	public ModelAndView filedownMethod(HttpServletRequest request, ModelAndView mv,
			@RequestParam("filename") String filename) {

		// 다운받을 파일명과 저장 폴더를 묶어서 File 객체를 만듦
		String savePath = request.getSession().getServletContext().getRealPath(
					"resources/test_upfiles");
		
		File downFile = new File(savePath + "\\" + filename);
		logger.info(downFile.toString());
		
		// 파일 다운 처리용 뷰클래스 id 명과 다운로드할 File 객체를 ModelAndView 에 담아서 리턴함
		mv.setViewName("filedown");
		mv.addObject("renameFile", downFile);
		mv.addObject("originFile", downFile);
		
		return mv;
	}
	
}









