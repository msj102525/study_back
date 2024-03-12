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
 * Servlet implementation class BoardMoveUpdatePageServlet
 */
@WebServlet("/bupview")
public class BoardMoveUpdatePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardMoveUpdatePageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 게시글 수정페이지 출력 요청 처리용 컨트롤러
		// 전송받은 글번호를 조회해서, 수정페이지에 수정할 글 내용이 출력되게 전달해야 함
		
		int boardNum = Integer.parseInt(request.getParameter("bnum"));
		int currentPage = Integer.parseInt(request.getParameter("page"));
		
		Board board = new BoardService().selectBoard(boardNum);
		
		RequestDispatcher view = null;
		if(board != null) {
			view = request.getRequestDispatcher("views/board/boardUpdateView.jsp");
			request.setAttribute("board", board);
			request.setAttribute("currentPage", currentPage);
		}else {
			view = request.getRequestDispatcher("views/common/error.jsp");
			request.setAttribute("message", boardNum + "번 게시글 수정페이지로 이동 실패!");
		}
		
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
