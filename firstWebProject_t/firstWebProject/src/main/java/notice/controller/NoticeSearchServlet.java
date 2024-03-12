package notice.controller;

import java.io.IOException;
import java.sql.Date;
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
 * Servlet implementation class NoticeSearchServlet
 */
@WebServlet("/nsearch")
public class NoticeSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NoticeSearchServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 공지사항 서비스에서 공지 검색 처리용 컨트롤러

		// 1. 전송온 값에 한글이 있다면 인코딩 처리함
		request.setCharacterEncoding("utf-8");

		// 2. 전송온 값 꺼내서 변수 또는 객체에 저장함
		String action = request.getParameter("action");
		String keyword = null, begin = null, end = null;

		if (action.equals("date")) {
			begin = request.getParameter("begin");
			end = request.getParameter("end");
		} else {
			keyword = request.getParameter("keyword");
			System.out.println("nsearch : " + keyword);
		}
		
		// 검색결과에 대한 페이징 처리 ----------------------------------------------------
		// 출력할 페이지 지정
		int currentPage = 1;
		// 전송온 페이지 값이 있다면 추출함
		if (request.getParameter("page") != null) {
			currentPage = Integer.parseInt(request.getParameter("page"));
		}

		// 한 페이지당 출력할 목록 갯수 지정
		int limit = 10;
		//전송온 목록 갯수가 있다면 추출함
		if(request.getParameter("limit") != null) {
			limit = Integer.parseInt(request.getParameter("limit"));
		}

		// 총 페이지 수 계산을 위한 검색 결과 목록 갯수 조회
		NoticeService nservice = new NoticeService();
		int listCount = 0;
		switch (action) {
		case "title":
			listCount = nservice.getSearchTitleCount(keyword);
			//System.out.println("listCount : " + listCount);
			break;
		case "content":
			listCount = nservice.getSearchContentCount(keyword);
			break;
		case "date":
			listCount = nservice.getSearchDateCount(Date.valueOf(begin), Date.valueOf(end));
			break;
		}

		// 뷰 페이지에서 사용할 페이징 관련 값 계산 처리
		Paging paging = new Paging(listCount, currentPage, limit, "nsearch");
		paging.calculator();
		//System.out.println("paging : " + paging);

		// 3. 모델 서비스 메소드로 값 전달 실행하고 결과 받기
		ArrayList<Notice> list = null;

		switch (action) {
		case "title":
			list = nservice.selectSearchTitle(keyword, paging);
			//System.out.println(list.size());
			break;
		case "content":
			list = nservice.selectSearchContent(keyword, paging);
			break;
		case "date":
			list = nservice.selectSearchDate(Date.valueOf(begin), Date.valueOf(end), paging);
			break;
		}

		// 4. 받은 결과로 성공/실페 페이지 내보내기
		RequestDispatcher view = null;
		if (list.size() > 0) {
			if(request.getParameter("admin") != null) {
				view = request.getRequestDispatcher("views/notice/noticeAdminListView.jsp");
			}else {
				view = request.getRequestDispatcher("views/notice/noticeListView.jsp");
			}
			
			request.setAttribute("list", list);
			request.setAttribute("paging", paging);
			request.setAttribute("currentPage", currentPage);
			request.setAttribute("limit", limit);
			request.setAttribute("action", action);
			
			if (action.equals("date")) {
				request.setAttribute("begin", begin);
				request.setAttribute("end", end);
			} else {
				request.setAttribute("keyword", keyword);
			}
		} else {
			view = request.getRequestDispatcher("views/common/error.jsp");
			if (keyword != null) {
				request.setAttribute("message", action + " 검색에 대한 " + keyword + "결과가 존재하지 않습니다.");
			} else {
				request.setAttribute("message", action + " 검색에 대한 " + begin + " 부터" + end + " 사이에 등록한 게시글이 존재하지 않습니다.");
			}
		}
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
