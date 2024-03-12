package notice.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.FileDownload;

/**
 * Servlet implementation class NoticeFileDownloadServlet
 */
@WebServlet("/nfdown")
public class NoticeFileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeFileDownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 공지사항 첨부파일 다운로드 처리용 컨트롤러
		
		//현재 파일이 저장된 폴더 지정
		String savePath = request.getSession().getServletContext()
				.getRealPath("/resources/notice_upfiles");
		
		//전송온 파일명에 한글이 있다면 인코딩처리함
		request.setCharacterEncoding("utf-8");
		
		//전송온 파일명 추출해서 변수에 저장
		String originalFileName = request.getParameter("ofile");
		String renameFileName = request.getParameter("rfile");
		
		FileDownload.down(savePath, originalFileName, renameFileName, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
