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
 * Servlet implementation class BoardDetailServlet
 */
@WebServlet("/bdetail")
public class BoardDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardDetailServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 게시글 (원글, 댓글, 대댓글) 상세보기 처리용 컨트롤러
		
		int boardNum = Integer.parseInt(request.getParameter("bnum"));
		
		//페이징 처리를 위한 페이지 변수
		int currentPage = 1;
		if(request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		
		//모델 서비스 객체 생성
		BoardService bservice = new BoardService();
		
		//해당 게시글에 대한 조회수 1증가 처리 : update
		bservice.addReadCount(boardNum);
		
		//해당 게시글 리턴 받음 : select
		Board board = bservice.selectBoard(boardNum);
		
		RequestDispatcher view = null;
		if(board != null) {
			view = request.getRequestDispatcher("views/board/boardDetailView.jsp");
			
			request.setAttribute("board", board);
			request.setAttribute("currentPage", currentPage);
		}else {
			view = request.getRequestDispatcher("views/common/error.jsp");
			
			request.setAttribute("message", boardNum + "번 글 상세조회 실패!");
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
