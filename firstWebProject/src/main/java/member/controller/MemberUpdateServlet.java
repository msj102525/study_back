package member.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import member.model.service.MemberService;
import member.model.vo.Member;

/**
 * Servlet implementation class MemberUpdateServlet
 */
@WebServlet("/mupdate")
public class MemberUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MemberUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 회원정보 수정 처리용 컨트롤러
		
		//1. 전송온 값에 한글이 있다면 인코딩 처리함
		request.setCharacterEncoding("UTF-8");
		
		//2. 전송온 값 꺼내서 변수 또는 객체에 저장하기
		Member member = new Member();
		
		member.setUserId(request.getParameter("userid"));
		member.setAge(Integer.parseInt(request.getParameter("age")));
		member.setPhone(request.getParameter("phone"));
		member.setEmail(request.getParameter("email"));
		
		//3. 모델 서비스 메소드로 값 또는 객체 전달 실행하고 결과받기
		int result = new MemberService().updateMember(member);
		
		//4. 받은 결과로 내보낼 뷰 선택 처리
		if(result > 0) {
			//수정 성공시 마이 페이지 뷰가 출력되게 한다면, myinfo 서블릿을 구동시킴
			//서블릿에서 다른 서블릿을 실행시킬 수 있음
			response.sendRedirect("/first/myinfo?userid=" + member.getUserId());
			
			//수정 성공시 메인페이지가 출력되게 한다면
			//response.sendRedirect("/first/index.jsp");
		}else {			
			RequestDispatcher view = request.getRequestDispatcher("views/common/error.jsp");
			//상대경로만 사용할 수 있음
			
			//지정한 뷰로 내보낼 정보나 객체가 있으면 request 에 기록 저장함
			//request.setAttribute(String name, Object value);
			request.setAttribute("message", member.getUserId() + " 회원 정보 수정 실패!");
			
			//뷰를 포워딩함
			view.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
