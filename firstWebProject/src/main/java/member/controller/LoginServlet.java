package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import member.model.dto.User;
import member.model.service.MemberService;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		// doGet() 메소드 : get 방식으로 전송보낸 요청 받는 메소드임
		
		// request 매개변수 :
		// 사용자(브라우저, view 페이지) input 으로 입력된 값 또는 쿼리스트링으로 전달된 값을 가지고 오는 객체
		// 전송방식이 get 이면 request head에 전송값이 기록되서 전송됨 => url 상에 보여짐
		// 전송방식이 post 이면 request body 에 전송값이 인코딩되서 저장되어 전송됨 => url 상에 안 보여짐
		// 서버측 연결대상의 정보와 전송방식도 저장함
		
		// response 매개변수 :
		// 서비스를 요청한 클라이언트 정보를 가지고 있음 (url, ip 주소 등)
		// 웹에서는 클라이언트와 서버 간의 (request, response) 가 쌍으로 요청과 응답을 위해 왔다갔다함
		
		
		// 로그인 처리용 컨트롤러
		// 컨트롤러의 코드 작성 순서와 내용이 비슷함 => 웹의 흐름을 빨리 파악하면 됨
		
		// 1. 전송온 값에 한글이 있다면 인코딩 처리함
		request.setCharacterEncoding("UTF-8"); // view 페이지의 문자셋과 맞춰줌
		
		// 2. 전송온 값 꺼내서 변수 또는 dto 객체에 저장 처리함
		// String userId = request.getParameter("userid"); // view의 input의 name 속성의 이름으로 값을 꺼냄
		// String usetPwd = request.getParameter("userpwd");
		
		// 객체에 저장한다면
		User user = new User();
		user.setUserId(request.getParameter("userid"));
		user.setUserPwd(request.getParameter("userpwd"));
		
		// 3. 모델측 서비스 클래스의 메소드를 실행하고 결과 받기
		// 메소드 실행시 값 또는 객체를 전달함
		User loginUser = new MemberService().selectLogin(user);
		
		// 4. 받은 결과를 가지고 성공 또는 실패 페이지 내보내기
		
		if(loginUser != null) { // 로그인 성공시
			// 로그인한 상태를 확인하기 위한 용도로 세션 객체를 생성함
			HttpSession session = request.getSession();
			// getSession() == getSession(true)
			// 세션객체가 없으면 새로 만들고, 있으면 그 객체를 리턴받음
			
			System.out.println("생성된 세션 객체의 id : " + session.getId());
			
			// 로그인한 회원의 정보를 세션 객체에 저장함
			session.setAttribute("loginUser", loginUser);
			
			// 로그인 성공시 내보낼 뷰페이지 지정 : 뷰만 내보낼 경우
			response.sendRedirect("index.jsp");
			
			
		} else { // 로그인 실패시
			// views/common/error.jsp 를 내보냄 + 오류 메세지(에러 이유) 도 함께 전송보냄
			// 뷰와 함께 같이 보낼 정보(데이터)가 있을 때는, RequestDispatcher 를 사용함
			// => 상대경로만 사용할 수 있음
			// 모든 컨트롤러(서블릿)의 실행 위치는 root (content directory : webapp)에서 실행되고 있음
			
			// 내보낼 뷰 지정함 : 상대경로 사용 가능함
			RequestDispatcher view = request.getRequestDispatcher("views/common/error.jsp");
			
			// 뷰와 함께 같이 보낼 데이터 처리 : request에 저장함 => Map 구조임 (key, value)
			request.setAttribute("message", "로그인 실패! 아이디와 암호를 다시 확인");
			
			// 로그인을 요청한 클라이언트로 내보냄
			view.forward(request, response);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// doPost() 메소드 : post 방식으로 전송보낸 요청을 받는 메소드임
		doGet(request, response);
	}

}
