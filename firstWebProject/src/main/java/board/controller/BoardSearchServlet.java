package board.controller;

import java.io.IOException;
import java.sql.Date;
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
 * Servlet implementation class MemberSearchServlet
 */
@WebServlet("/bsearch")
public class BoardSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 게시글 서비스에서 검색 처리용 컨트롤러
		
		//1. 전송온 값에 한글이 있다면 인코딩 처리함
		request.setCharacterEncoding("utf-8");
		
		//2. 전송온 값 꺼내서 변수 또는 객체에 저장함
		String action = request.getParameter("action");
		String keyword = null, begin = null, end = null;
		
		if(action.equals("date")) {
			begin = request.getParameter("begin");
			end = request.getParameter("end");
		}else {
			keyword = request.getParameter("keyword");
		}
		
		//검색결과에 대한 페이징 처리 ----------------------------------------------------
		//출력할 페이지 지정
		int currentPage = 1;
		//전송온 페이지 값이 있다면 추출함
		if(request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}		
		
		//한 페이지당 출력할 목록 갯수 지정
		int limit = 10;
		
		//총 페이지 수 계산을 위한 검색 결과 목록 갯수 조회
		BoardService bservice = new BoardService();
		int listCount = 0;	
		switch(action) {
		case "title": 		listCount = bservice.getSearchTitleCount(keyword);	break;
		case "writer":		listCount = bservice.getSearchWriterCount(keyword);	break;		
		case "date":	listCount = bservice.getSearchDateCount(
							Date.valueOf(begin), Date.valueOf(end));		break;		
		}
		
		//뷰 페이지에서 사용할 페이징 관련 값 계산 처리
		Paging paging = new Paging(listCount, currentPage, limit, "bsearch");
		paging.calculator();
		
		//3. 모델 서비스 메소드로 값 전달 실행하고 결과 받기		
		ArrayList<Board> list = null;
		
		switch(action) {
		case "title": 		list = bservice.selectSearchTitle(keyword, paging);	break;
		case "writer":		list = bservice.selectSearchWriter(keyword, paging);	break;		
		case "date":	list = bservice.selectSearchDate(
							Date.valueOf(begin), Date.valueOf(end), paging);		break;		
		}
		
		
		
		//4. 받은 결과로 성공/실페 페이지 내보내기
		RequestDispatcher view = null;
		if(list.size() > 0) {
			view = request.getRequestDispatcher("views/board/boardListView.jsp");
			request.setAttribute("list", list);
			request.setAttribute("paging", paging);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("action", action);
			if(action.equals("date")) {
				request.setAttribute("begin", begin);
				request.setAttribute("end", end);
			}else {
				request.setAttribute("keyword", keyword);
			}
		}else {
			view = request.getRequestDispatcher("views/common/error.jsp");
			if(keyword != null) {
				request.setAttribute("message", 
					action + " 검색에 대한 " + keyword + "결과가 존재하지 않습니다.");
			}else {
				request.setAttribute("message", 
						action + " 검색에 대한 " + begin + " 부터" + end 
						+ " 사이에 등록한 게시글이 존재하지 않습니다.");
			}
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
