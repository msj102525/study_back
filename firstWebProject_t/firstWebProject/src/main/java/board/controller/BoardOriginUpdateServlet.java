package board.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import board.model.service.BoardService;
import board.model.vo.Board;
import common.FileNameChange;

/**
 * Servlet implementation class BoardUpdateServlet
 */
@WebServlet("/boriginupdate")
public class BoardOriginUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BoardOriginUpdateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 게시글 원글 수정 처리용 컨트롤러

		// 1. multipart 방식으로 인코딩되어서 전송왔는지 확인
		// 아니면 에러 페이지를 내보냄
		RequestDispatcher view = null;
		if (!ServletFileUpload.isMultipartContent(request)) {
			view = request.getRequestDispatcher("views/common/error.jsp");
			request.setAttribute("message", "form의 enctype='multipart/form-data' 속성 누락됨.");
			view.forward(request, response);
		}

		// 2. 업로드할 파일의 용량 제한 설정 : 10메가바이트로 제한한다면
		int maxSize = 1024 * 1024 * 10;

		// 3. 업로드되는 파일의 저장 폴더 지정
		String savePath = request.getSession().getServletContext().getRealPath("/resources/board_upfiles");
		// request.getSession().getServletContext() => "/first" + 뒤에 하위 폴더 경로 추가함

		// 4. request 를 MultipartRequest 로 변환해야 함
		// MultipartRequest 클래스는 외부 라이브러리를 사용해야 함 : cos.jar 사용한 경우
		// MultipartRequest 객체가 생성되면, 자동으로 지정 폴더에 업로드된 파일이 저장됨
		MultipartRequest mrequest = new MultipartRequest(request, savePath, maxSize, "UTF-8",
				new DefaultFileRenamePolicy());

		// 5. 데이터베이스 board 테이블에 기록할 값 추출
		// mrequest 사용해야 함 (request 사용 못 함)
		Board board = new Board();

		board.setBoardTitle(mrequest.getParameter("title"));		
		board.setBoardContent(mrequest.getParameter("content"));
		board.setBoardNum(Integer.parseInt(mrequest.getParameter("bnum")));
		
		int currentPage = Integer.parseInt(mrequest.getParameter("page"));

		//이전 첨부파일에 대한 삭제여부 값 추출
		String deleteFlag = mrequest.getParameter("deleteFlag");		
		
		//이전 첨부파일의 파일명 추출
		String originalFilePath = mrequest.getParameter("ofile");
		String renameFilePath = mrequest.getParameter("rfile");
		
		// 6. 새로 업로드된 원본 파일이름 추출
		String originalFileName = mrequest.getFilesystemName("upfile");
		
		//첨부파일 확인 : 
		// 원래 첨부파일과 새로 업로드된 파일의 이름이 같고, 파일 용량도 동일하면
		// 그대로 board 에 기록함
		// 파일 객체 만들어서 파일크기 비교함
		File newOriginFile = new File(savePath + "\\" + originalFileName);
		File originFile = new File(savePath + "\\" + originalFilePath);
		
		if(originalFilePath.equals(originalFileName) == true 
				&& newOriginFile.length() == originFile.length()) {
			board.setBoardOriginalFileName(originalFilePath);
			board.setBoardRenameFileName(renameFilePath);
		}
		
		//원래 첨부파일이 있었는데, 변경되지 않은 경우
		if(originalFilePath != null && originalFileName == null) {
			board.setBoardOriginalFileName(originalFilePath);
			board.setBoardRenameFileName(renameFilePath);
		}
		
		//원래 첨부파일이 있었는데 파일삭제가 체크된 경우
		if(originalFilePath != null && deleteFlag != null && deleteFlag.equals("yes")) {
			
			board.setBoardOriginalFileName(null);
			board.setBoardRenameFileName(null);
			
			//저장 폴더에서 제거함
			new File(savePath + "\\" + renameFilePath).delete();
		}

		// 첨부파일이 없었는데 새로 추가된 경우와
		// 첨부파일이 있었는데 변경된 경우 둘 다 처리
		if (originalFileName != null) {
			// 새로 업로드된 파일이 있다면
			board.setBoardOriginalFileName(originalFileName);

			String renameFileName = FileNameChange.change(
					originalFileName, savePath, "yyyyMMddHHmmss");

			board.setBoardRenameFileName(renameFileName);
			
			//이전 첨부된 파일이 있었다면 저장 폴더에서 파일 삭제함
			if(originalFilePath != null) {
				new File(savePath + "\\" + renameFilePath).delete();
			}
		} // 새로 업로드된 파일이 있다면...

		// 모델 서비스 메소드로 전달하고 결과받기
		int result = new BoardService().updateOriginBoard(board);

		// 받은 결과로 성공/실패 페이지 내보내기
		if (result > 0) {
			// 서블릿에서 서블릿을 실행함
			response.sendRedirect("/first/bdetail?bnum=" + board.getBoardNum() 
								+ "&page=" + currentPage);
		} else {
			view = request.getRequestDispatcher("views/common/error.jsp");
			request.setAttribute("message", board.getBoardNum() + "번 게시 원글 수정 실패.");
			view.forward(request, response);
		}
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
