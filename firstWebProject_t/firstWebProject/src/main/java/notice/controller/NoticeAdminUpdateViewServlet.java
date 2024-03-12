package notice.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notice.model.service.NoticeService;
import notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeAdminUpdateViewServlet
 */
@WebServlet("/nmoveup.admin")
public class NoticeAdminUpdateViewServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeAdminUpdateViewServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 관리자용 : 공지글 수정페이지로 이동 처리용 컨트롤러
		
		int noticeno = Integer.parseInt(
				request.getParameter("noticeno"));
		
		Notice notice = new NoticeService().selectOne(noticeno);
		
		RequestDispatcher view = null;
		if(notice != null) {
			view = request.getRequestDispatcher(
					"views/notice/noticeUpdateView.jsp");
			request.setAttribute("notice", notice);
			view.forward(request, response);			
		}else {
			view = request.getRequestDispatcher(
					"views/common/error.jsp");
			request.setAttribute("message", noticeno 
					+ "글에 대한 수정페이지 이동 처리 실패!");
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
