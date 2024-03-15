package org.myweb.first.member.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.myweb.first.member.model.service.MemberService;
import org.myweb.first.member.model.vo.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.support.SessionStatus;

@Controller // 설정 xml에 해당 클래스를 Controller 로 자동 등록해 줌
public class MemberController {
	// 이 클래스 안의 메소드들이 잘 동작하는지 또는 전달값이나 리턴값을 확인하기 위한 용도로 로그 객체를 생성함
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	// 서비스 연결  처리 : 자동 DI 처리
	@Autowired
	private MemberService memberService; //= new MemberServiceImpl();
	// 스프링에서는 부모 인터페이스 타입으로 레퍼런스 선언함 (다형성 이용함)
	// 실행시 후손의 오버라이딩된 메소드를 연결 실행하게 됨 (동적 바인딩 이라고 함)
	
	// 뷰 페이지 내보내기용 메소드 ----------------------
	@RequestMapping(value="loginPage.do", method={RequestMethod.GET, RequestMethod.POST})
	public String moveLoginPage() {
		return "member/loginPage";
	}
	
	// 요청 받아서 모델쪽 서비스로 넘기고 결과받는 메소드 -------------------------
	// 로그인 처리용 메소드 : Servlet 방식으로 처리
	/*
	 * @RequestMapping(value="login.do", method=RequestMethod.POST) 
	 * public String loginMethod(HttpServletRequest request, HttpServletResponse response, Model model) {
	 * 
	 *  logger.info("login.do run()...");
	 * 
	 * //1. 전송온 값 꺼내서 객체에 저장하기 Member member = new Member();
	 * member.setUserId(request.getParameter("userid"));
	 * member.setUserPwd(request.getParameter("userpwd"));
	 * 
	 * //2. 서비스 메소드로 객체 전달하고 실행된 결과 받기 
	 * Member loginMember = memberService.selectLogin(member); logger.info(loginMember.toString());
	 * 
	 * //3. 받은 결과를 가지고 성공 또는 실패 페이지 내보내기 if(loginMember != null) { // 로그인 세션 생성함
	 * HttpSession session = request.getSession(); // 인터페이스
	 * session.setAttribute("loginMember", loginMember); return"common/main"; 
	 * } else { 
	 * // 스프링에서는 request에 저장처리하는 내용(객체정보)은 스프링이 제공하는 Model을 사용함 
	 * // 포워딩 못 함 => ViewResolver로 뷰파일명과 뷰로 내보낼 값이 전달되는 구조
	 *  model.addAttribute("message",	 "로그인 실패! 아이디나 암호를 다시 확인하세요." ); 
	 *  return "common/error"; 
	 * } }
	 */
	
	// 로그인 처리용 메소드 : command 객체 사용
	// 서버로 전송온 parameter 값을 저장하는 객체를 command 객체라고 함
	// input 태그의 name 속성의 이름과 vo 객체의 필드명이 같으면 자동으로 command 객체가 값을 받음
	@RequestMapping(value="login.do", method=RequestMethod.POST)
	public String loginMethod(Member member, HttpSession session, SessionStatus status, Model model) {
		logger.info("login.do : " + member.toString());
		
		// 서비스 메소드로 보내고 결과받기
		Member loginMember = memberService.selectLogin(member);
		
		if(loginMember != null) {
			session.setAttribute("loginMember", loginMember);
			status.setComplete(); // 로그인 성공 요청 결과로 HttpStatus code 200 보냄
			return "common/main";
		} else {
			model.addAttribute("message", "로그인 실패! 아이디나 암호를 다시 확인하세요.");
			return "common/error";
		}
		
	}
	
	// 로그아웃 처리용 메소드
	// 요청에 대한 전송방식이 get 이면, method 속성 생략해도 됨, method 속성을 생략하면 value 속성 생략해도 됨,
	// method 속성을 생략하면 value 속성도 표기 생략 가능
	@RequestMapping("logout.do")
	public String logoutMethod(HttpServletRequest request, Model model) {
		HttpSession session = request.getSession(false);
		// 세션 객체가 있으면 리턴받고, 없으면 null 리턴
		
		if(session != null) {
			session.invalidate();
			return "common/main";
		} else {
			model.addAttribute("message", "로그인 세션이 존재하지 않습니다.");
			return "common/error";
		}
		
	}
	
	
}
