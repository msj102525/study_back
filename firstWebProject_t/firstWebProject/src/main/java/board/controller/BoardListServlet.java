package board.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import board.model.service.BoardService;
import board.model.vo.Board;
import common.Paging;

/**
 * Servlet implementation class BoardListServlet
 */
@WebServlet("/blist")
public class BoardListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 페이지별로 출력되는 게시글 목록 조회 요청 처리용 컨트롤러
		
		//출력할 페이지 지정
		int currentPage = 1;
		//전송온 페이지 값이 있다면 추출함
		if(request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		//System.out.println("blist : " + currentPage);
		
		//한 페이지당 출력할 목록 갯수 지정
		int limit = 10;
		
		//조회용 모델측 서비스 객체 생성
		BoardService bservice = new BoardService();
		
		//총 페이지 수 계산을 위한 전체 목록 갯수 조회
		int listCount = bservice.getListCount();
		//System.out.println("blist : " + listCount);
		
		//뷰 페이지에서 사용할 페이징 관련 값 계산 처리
		Paging paging = new Paging(listCount, currentPage, limit, "blist");
		paging.calculator();
		//System.out.println(paging);
		
		//모델 서비스로 해당 페이지에 출력할 게시글만 조회해 옴
		ArrayList<Board> list = bservice.selectList(paging.getStartRow(), paging.getEndRow());
			
		//받은 결과에 따라 성공 또는 실패 페이지 내보내기
		RequestDispatcher view = null;
		if(list.size() > 0) {
			view = request.getRequestDispatcher("views/board/boardListView.jsp");
			
			request.setAttribute("list", list);
			request.setAttribute("paging", paging);	
			request.setAttribute("currentPage", currentPage);
		}else {
			view = request.getRequestDispatcher("views/common/error.jsp");
			
			request.setAttribute("message", currentPage + "페이지에 대한 목록 조회 실패");
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
