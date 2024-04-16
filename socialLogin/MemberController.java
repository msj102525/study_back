package org.ict.first.member.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.jackson.JsonNode;
import org.ict.first.common.SearchDate;
import org.ict.first.common.Searchs;
import org.ict.first.member.model.service.MemberService;
import org.ict.first.member.model.vo.KakaoMember;
import org.ict.first.member.model.vo.Member;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.ModelAndView;

@Controller // xml 에 클래스를 controller 로 자동 등록해 줌
public class MemberController {
	// 이 컨트롤로 안의 메소드들이 구동되었는지 확인하는 로그를
	// 출력하기 위한 로그 객체를 생성
	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);

	@Autowired // 자동 의존성주입(DI) : 자동 객체 생성됨
	private MemberService memberService;

	@Autowired
	private BCryptPasswordEncoder bcryptPasswordEncoder;

	@Autowired
	private KakaoLoginAuth kakaoLoginAuth;

	@Autowired
	private GoogleLoginAuth googleLoginAuth;

	@Autowired
	private NaverLoginAuth naverLoginAuth;

	// 웹 서비스 요청 하나당 메소드 하나씩 작성하는 방식임 *****
	// 뷰 페이지 이동 처리용 ------------------------------------

//	//로그인 페이지 내보내기용 메소드
//	@RequestMapping(value="loginPage.do", method={ RequestMethod.GET, RequestMethod.POST })
//	public String moveLoginPage() {
//		return "member/loginPage";
//	}  

	// 소셜로그인이 포함된 로그인 페이지 내보내기용 메소드
	@RequestMapping(value = "loginPage.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String moveLoginPage(Model model, HttpSession session) {
		// 카카오 로그인 접속을 위한 인증 url 정보 생성
		String kakaoAuthURL = kakaoLoginAuth.getAuthorizationUrl(session);

		// 네이버 로그인 접속을 위한 인증 url 정보 생성
		String naverAuthURL = naverLoginAuth.getAuthorizationUrl(session);

		// 구글 로그인 접속을 위한 인증 url 정보 생성
		String googleAuthURL = googleLoginAuth.getAuthorizationUrl(session);

		// 모델에 각각의 url 정보 저장
		model.addAttribute("kakaourl", kakaoAuthURL);
		model.addAttribute("googleourl", googleAuthURL);
		model.addAttribute("naverurl", naverAuthURL);

		return "member/loginPage";
	}

	// 카카오 로그인 요청 처리용
	// (카카오 로그인 클릭시 전달된 kakaourl 에 의해 실행됨)
	@RequestMapping(value = "kcallback.do", produces = "application/json", method = { RequestMethod.GET,
			RequestMethod.POST })
	public String kakaoLogin(@RequestParam String code,
			Model model, HttpSession session) {
		logger.info("0. kcallback.do : " + code);
		
		//로그인 결과값을 node에 담아줌
		JsonNode node = kakaoLoginAuth.getAccessToken(code);
		logger.info("1. kcallback.do : " + node);
		// accessToken에 사용자의 로그인한 모든 정보가 들어있음
		JsonNode accessToken = node.get("access_token");
		logger.info("2. kcallback.do : " + accessToken);
		// 사용자 정보 추출
		JsonNode userInfo = kakaoLoginAuth.getKakaoUserInfo(accessToken);
		logger.info("3. kcallback.do : " + userInfo);
		
		// db table 에 기록할 회원정보 추출함 : 카카오 회원가입시
		//userInfo 에서 properties 정보 추출
		JsonNode properties = node.get("properties");
		logger.info("4. kcallback.do : " + properties);
		
		JsonNode kakao_account = userInfo.path("kakao_account");
		String kid = userInfo.path("id").asText();
		logger.info("5. kcallback.do : " + kakao_account);
		
		//등록된 카카오 회원 테이블에서 회원 정보 조회해 옴
		KakaoMember kmember = 
				memberService.selectKakaoLogin(kid);		
		
		Member loginMember = null; 
		
		//처음 로그인 요청시 카카오 회원 테이블에 회원 정보 저장
		if(kmember == null) {
			KakaoMember kakaovo = new KakaoMember();
			//properties 에서 하나씩 꺼내서 member 에 저장 처리
			kakaovo.setUserid(kid);
			kakaovo.setUsername((String)properties.get("nickname").asText());
			kakaovo.setEmail((String)kakao_account.get("email").asText());
				
			logger.info("6. kcallback.do : " + kakaovo);
			
			memberService.insertKakaoMember(kakaovo);
			loginMember = kakaovo;
		}else {
			loginMember = kmember;
		}
				
		if (loginMember != null) {
			// 카카오 로그인 성공시
			session.setAttribute("loginMember", loginMember);
			return "redirect:main.do";
		} else {
			model.addAttribute("message", "카카오 로그인 실패!");
			return "common/error";
		}
	}

	// 네이버 로그인 요청 처리용
	// (네이버 로그인 클릭시 전달된 naverurl 에 의해 실행됨)
	@RequestMapping(value = "ncallback.do", 
			method = { RequestMethod.GET, 	RequestMethod.POST })
	public String naverLogin(Model model, HttpSession session) {

		Member loginMember = null;
		
		if (loginMember != null) {
			// 카카오 로그인 성공시
			session.setAttribute("loginMember", loginMember);
			return "redirect:main.do";
		} else {
			model.addAttribute("message", "카카오 로그인 실패!");
			return "common/error";
		}
	}

	// 구글 로그인 요청 처리용
	// (구글 로그인 클릭시 전달된 googleurl 에 의해 실행됨)
	@RequestMapping(value = "gcallback.do", 
			method = { RequestMethod.GET, 	RequestMethod.POST })
	public String googleLogin(
			Model model, HttpSession session) {

		Member loginMember = null;
		
		if (loginMember != null) {
			// 카카오 로그인 성공시
			session.setAttribute("loginMember", loginMember);
			return "redirect:main.do";
		} else {
			model.addAttribute("message", "카카오 로그인 실패!");
			return "common/error";
		}
	}

	// 회원가입 페이지 내보내기용
	@RequestMapping("enrollPage.do")
	public String moveEnrollPage() {
		return "member/enrollPage";
	}

	// 회원정보 수정페이지 내보내기용
	@RequestMapping("moveup.do")
	public String moveUpdatePage(@RequestParam("userid") String userid, Model model) {
		Member member = memberService.selectMember(userid);

		if (member != null) {
			model.addAttribute("member", member);
			return "member/updatePage";
		} else {
			model.addAttribute("message", userid + " : 회원 조회 실패!");
			return "common/error";
		}
	}

	// 서비스와 연결되는 요청 처리용 ----------------------------

	// 로그인 처리용 메소드 : Servlet 방식으로 처리
//	@RequestMapping(value="login.do", method=RequestMethod.POST)
//	public String loginMethod(HttpServletRequest request, 
//			HttpServletResponse response, Model model) {
//		
//		//1. 전송온 값 꺼내기		
//		Member member = new Member();
//		member.setUserid(request.getParameter("userid"));
//		member.setUserpwd(request.getParameter("userpwd"));
//		
//		//2. 서비스 모델로 전송하고 결과 받기
//		Member loginMember = memberService.selectLogin(member);
//		
//		//3. 받은 결과를 가지고 성공/실패 페이지를 선택해서 리턴함
//		if(loginMember != null) {
//			//세션 생성
//			HttpSession session = request.getSession();
//			session.setAttribute("loginMember", loginMember);
//			return "common/main";
//		}else {
//			model.addAttribute("message", "로그인 실패!");
//			return "common/error";
//		}
//	}

	// 로그인 처리용 메소드 : command 객체 사용
	// 서버로 전송 온 parameter 값을 저장하는 객체를 command 객체라고 함
	// input 태그의 name 과 vo 객체의 필드명이 같으면 됨
	@RequestMapping(value = "login.do", method = RequestMethod.POST)
	public String loginMethod(Member member, HttpSession session, SessionStatus status, Model model) {
		logger.info("login.do : " + member);

		// 서비스 모델로 전달하고 결과 받기
		// Member loginMember = memberService.selectLogin(member);

		// 암호화 처리된 패스워드 일치 조회는
		// select 해 온 값으로 비교함
		// 전달온 회원 아이디로 먼저 정보조회함
		Member loginMember = memberService.selectMember(member.getUserid());

		// 암호화된 패스워드와 전송온 글자타입 패스워드를 비교함
		// matches(글자타입패스워드, 암호화된패스워드)
		if (loginMember != null && this.bcryptPasswordEncoder.matches(member.getUserpwd(), loginMember.getUserpwd())) {
			session.setAttribute("loginMember", loginMember);
			status.setComplete(); // 로그인 요청 성공, 200 전송함
			return "common/main";
		} else {
			model.addAttribute("message", "로그인 실패 : 아이디나 암호 확인하세요.<br>" + "또는 로그인 제한된 회원인지 관리자에게 문의하세요.");
			return "common/error";
		}

	}

	@RequestMapping("logout.do")
	public String logoutMethod(HttpServletRequest request, Model model) {

		HttpSession session = request.getSession(false);
		logger.info("logout.do : " + session);

		if (session != null) {
			session.invalidate();
			return "common/main";
		} else {
			model.addAttribute("message", "로그인 세션이 존재하지 않습니다");
			return "common/error";
		}
	}

	// ajax 통신으로 아이디 중복확인 요청 처리용 메소드
	@RequestMapping(value = "idchk.do", method = RequestMethod.POST)
	public void dupCheckIdMethod(@RequestParam("userid") String userid, HttpServletResponse response)
			throws IOException {
		int idCount = memberService.selectDupCheckId(userid);

		String returnStr = null;
		if (idCount == 0) {
			returnStr = "ok";
		} else {
			returnStr = "duple";
		}

		// response 를 이용해서 클라이언트와 출력스트림을 연결하고 값 보냄
		response.setContentType("text/html; charset=utf-8");
		PrintWriter out = response.getWriter();
		out.append(returnStr);
		out.flush();
		out.close();
	}

	// 회원가입 요청 처리용 메소드
	@RequestMapping(value = "enroll.do", method = RequestMethod.POST)
	public String memberInsertMethod(Member member, Model model) {
		logger.info("enroll.do : " + member);

		// 패스워드 암호화 처리
		member.setUserpwd(bcryptPasswordEncoder.encode(member.getUserpwd()));
		logger.info("after encode : " + member.getUserpwd());
		logger.info("length : " + member.getUserpwd().length());

		if (memberService.insertMember(member) > 0) {
			// 회원 가입 성공
			return "common/main";
		} else {
			// 회원 가입 실패
			model.addAttribute("message", "회원 가입 실패!");
			return "common/error";
		}
	}

	// 마이페이지 클릭시 내 정보 보기 요청 처리용 메소드
	// 리턴 타입은 String, ModelAndView 를 사용할 수 있음
	@RequestMapping("myinfo.do")
	public ModelAndView memberDetailMethod(@RequestParam("userid") String userid, ModelAndView mv) {
		// 서비스로 아이디 전달하고, 해당 회원정보 받기
		Member member = memberService.selectMember(userid);

		if (member != null) {
			mv.addObject("member", member);
			// Model 또는 ModelAndView 에 저장하는 것은
			// request.setAttribute("member", member); 과 같음
			mv.setViewName("member/myinfoPage");
		} else {
			mv.addObject("message", userid + " : 회원 정보 조회 실패!");
			mv.setViewName("common/error");
		}

		return mv;
	}

	// 회원 탈퇴(삭제) 요청 처리용
	@RequestMapping("mdel.do")
	public String memberDeleteMethod(@RequestParam("userid") String userid, Model model) {
		logger.info("mdel.do : " + userid);

		if (memberService.deleteMember(userid) > 0) {
			// 회원 탈퇴 성공시, 자동 로그아웃 처리해야 함
			// 컨트롤러 메소드에서 다른 [컨트롤러] 메소드 호출할 수 있음
			return "redirect:logout.do";
		} else {
			model.addAttribute("message", userid + " : 회원 삭제 실패!");
			return "common/error";
		}
	}

	// 회원정보 수정 처리용 : 수정 성공시 myinfoPage.jsp 로 이동함
	@RequestMapping(value = "mupdate.do", method = RequestMethod.POST)
	public String memberUpdateMethod(Member member, Model model, @RequestParam("origin_userpwd") String originUserpwd) {

		logger.info("mupdate.do : " + member);

		// 새로운 암호가 전송이 왔다면, 패스워드 암호화 처리함
		String userpwd = member.getUserpwd().trim();
		if (userpwd != null && userpwd.length() > 0) {
			// 암호화된 기존의 패스워드와 새로운 패스워드를
			// 비교해서 다른 값이면
			if (!this.bcryptPasswordEncoder.matches(userpwd, originUserpwd)) {
				// member 에 새로운 패스워드를 암호화해서 기록함
				member.setUserpwd(this.bcryptPasswordEncoder.encode(userpwd));
			}
		} else {
			// 새로운 패스워드 값이 없다면, member 에 원래 패스워드 기록
			member.setUserpwd(originUserpwd);
		}

		if (memberService.updateMember(member) > 0) {
			// 수정이 성공했다면, 컨트롤러의 메소드를 직접 호출함
			// 필요시, 값을 전달할 수도 있음 : 쿼리스트링 사용함
			// ?이름=값&이름=값
			return "redirect:myinfo.do?userid=" + member.getUserid();
		} else {
			model.addAttribute("message", member.getUserid() + " : 회원 정보 수정 실패!");
			return "common/error";
		}
	}

	// 회원관리용 회원전체목록 조회 처리용
	@RequestMapping("mlist.do")
	public String memberListViewMethod(Model model) {
		ArrayList<Member> list = memberService.selectList();

		if (list != null && list.size() > 0) {
			model.addAttribute("list", list);
			return "member/memberListView";
		} else {
			model.addAttribute("message", "회원 정보가 존재하지 않습니다.");
			return "common/error";
		}
	}

	// 관리자 기능 : 회원 로그인 제한/가능 처리용 메소드
	@RequestMapping("loginok.do")
	public String changeLoginOKMethod(Member member, Model model) {
		logger.info("loginok.do : " + member.getUserid() + ", " + member.getLogin_ok());
		if (memberService.updateLoginok(member) > 0) {
			return "redirect:mlist.do";
		} else {
			model.addAttribute("message", "로그인 제한/허용 처리 오류 발생!");
			return "common/error";
		}
	}

	// 회원 검색 처리용
	@RequestMapping(value = "msearch.do", method = RequestMethod.POST)
	public String memberSearchMethod(HttpServletRequest request, Model model) {
		// 전송온 값 꺼내기
		String action = request.getParameter("action");
		String keyword = null, beginDate = null, endDate = null;

		if (action.equals("enroll")) {
			beginDate = request.getParameter("begin");
			endDate = request.getParameter("end");
		} else {
			keyword = request.getParameter("keyword");
		}

		// 서비스 메소드가 리턴하는 값을 받을 리스트 준비
		ArrayList<Member> list = null;
		Searchs searchs = new Searchs();

		switch (action) {
		case "id":
			searchs.setKeyword(keyword);
			list = memberService.selectSearchUserid(searchs);
			break;
		case "gender":
			searchs.setKeyword(keyword);
			list = memberService.selectSearchGender(searchs);
			break;
		case "age":
			searchs.setAge(Integer.parseInt(keyword));
			list = memberService.selectSearchAge(searchs);
			break;
		case "enroll":
			list = memberService.selectSearchEnrollDate(new SearchDate(Date.valueOf(beginDate), Date.valueOf(endDate)));
			break;
		case "login":
			searchs.setKeyword(keyword);
			list = memberService.selectSearchLoginOK(searchs);
			break;
		} // switch

		if (list != null && list.size() > 0) {
			model.addAttribute("list", list);
			return "member/memberListView";
		} else {
			model.addAttribute("message", action + " 검색에 대한 결과가 존재하지 않습니다.");
			return "common/error";
		}
	}
}
