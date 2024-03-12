package board.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Board;

/**
 * Servlet implementation class BoardReplyUpdateServlet
 */
@WebServlet("/breplyupdate")
public class BoardReplyUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardReplyUpdateServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 게시글 댓글과 대댓글 수정 처리용 컨트롤러
		
		request.setCharacterEncoding("utf-8");
		
		//페이지 추출		
		int currentPage = Integer.parseInt(request.getParameter("page"));
		
		//댓글 정보 저장 객체 준비
		Board reply = new Board();
		
		//전송값 꺼내서 객체 필드에 저장
		reply.setBoardTitle(request.getParameter("title"));		
		reply.setBoardContent(request.getParameter("content"));
		reply.setBoardNum(Integer.parseInt(request.getParameter("bnum")));
				
		//서비스 메소드 실행하고 결과받기
		int result = new BoardService().updateReplyBoard(reply);
		
		// 받은 결과로 성공/실패 페이지 내보내기
		if (result > 0) {
			// 서블릿에서 서블릿을 실행함
			response.sendRedirect("/first/bdetail?bnum=" + reply.getBoardNum() 
								+ "&page=" + currentPage);
		} else {
			RequestDispatcher view = request.getRequestDispatcher("views/common/error.jsp");
			request.setAttribute("message", reply.getBoardNum() + "번 게시 댓글 수정 실패.");
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
