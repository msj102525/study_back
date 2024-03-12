package notice.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.Paging;
import notice.model.service.NoticeService;
import notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeListServlet
 */
@WebServlet("/nlist")
public class NoticeListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 공지사항 목록보기 처리용 컨트롤러
		
		//출력할 페이지 지정
		int currentPage = 1;
		//전송온 페이지 값이 있다면 추출함
		if(request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}
		
		//한 페이지당 출력할 목록 갯수 지정
		int limit = 10;
		//전송온 목록 갯수가 있다면 추출함
		if(request.getParameter("limit") != null) {
			limit = Integer.parseInt(request.getParameter("limit"));
		}
		//System.out.println("limit : " + limit);
		
		//조회용 모델측 서비스 객체 생성
		NoticeService nservice = new NoticeService();
		
		//총 페이지 수 계산을 위한 전체 목록 갯수 조회
		int listCount = nservice.getListCount();
		
		//뷰 페이지에서 사용할 페이징 관련 값 계산 처리
		Paging paging = new Paging(listCount, currentPage, limit, "nlist");
		paging.calculator();
		//System.out.println(paging);
		
		//모델 서비스로 해당 페이지에 출력할 공지사항만 조회해 옴
		ArrayList<Notice> list = nservice.selectList(paging);
	
		RequestDispatcher view = null;
		if(list.size() > 0) {
			view = request.getRequestDispatcher("views/notice/noticeListView.jsp");
			request.setAttribute("list", list);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("limit", limit);
			request.setAttribute("paging", paging);			
		}else {
			view = request.getRequestDispatcher(
					"views/common/error.jsp");
			request.setAttribute("message", "공지사항 목록 조회 실패!");			
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
