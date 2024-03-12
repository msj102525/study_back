package board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import common.FileDownload;

/**
 * Servlet implementation class BoardFileDownloadServlet
 */
@WebServlet("/bfdown")
public class BoardFileDownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardFileDownloadServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 게시글의 파일 다운로드 처리용 컨트롤러
		
		//전송온 파일명에 한글이 있다면 인코딩처리함
		request.setCharacterEncoding("utf-8");
		
		//전송온 파일명 추출해서 변수에 저장
		String originalFileName = request.getParameter("ofile");
		String renameFileName = request.getParameter("rfile");
		
		//저장 폴더에서 파일읽기용 스트림 생성
		String savePath = request.getSession().getServletContext()
								.getRealPath("/resources/board_upfiles");
		
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
