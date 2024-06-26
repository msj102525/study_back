package board.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import board.model.service.BoardService;
import board.model.vo.Board;
import common.FileNameChange;

/**
 * Servlet implementation class BoardOriginInsertServlet
 */
@WebServlet("/binsert")
public class BoardOriginInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BoardOriginInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		// 게시글 원글 등록 처리용 컨트롤러 : 파일업로드가 있는 전송인 경우임
		
		//1. multipart 방식으로 인코딩되어서 전송왔는지 확인
		//아니면 에러 페이지를 내보냄
		RequestDispatcher view = null;
		if(!ServletFileUpload.isMultipartContent(request)) {
			view = request.getRequestDispatcher("views/common/error.jsp");
			request.setAttribute("message", "form의 enctype='multipart/form-data' 속성 누락됨.");
			view.forward(request, response);
		}
		
		//2. 업로드할 파일의 용량 제한 설정 : 10메가바이트로 제한한다면
		int maxSize = 1024 * 1024 * 10;
		
		//3. 업로드되는 파일의 저장 폴더 지정
		String savePath = request.getSession().getServletContext().getRealPath("/resources/board_upfiles");
		//request.getSession().getServletContext()  => "/first"  + 뒤에 하위 폴더 경로 추가함
		
		//4. request 를 MultipartRequest 로 변환해야 함
		//MultipartRequest 클래스는 외부 라이브러리를 사용해야 함 : cos.jar 사용한 경우
		//MultipartRequest 객체가 생성되면, 자동으로 지정 폴더에 업로드된 파일이 저장됨
		MultipartRequest mrequest = new MultipartRequest(request, savePath, maxSize, 
				"UTF-8", new DefaultFileRenamePolicy());
		
		//5. 데이터베이스 board 테이블에 기록할 값 추출
		//mrequest 사용해야 함 (request 사용 못 함)
		Board board = new Board();
		
		board.setBoardTitle(mrequest.getParameter("title"));
		board.setBoardWriter(mrequest.getParameter("writer"));
		board.setBoardContent(mrequest.getParameter("content"));
		
		//6. 업로드된 원본 파일이름 추출
		String originalFileName = mrequest.getFilesystemName("upfile");
		board.setBoardOriginalFileName(originalFileName);
		
		//7. 폴더에 저장된 파일의 이름바꾸기 처리
		// 저장 폴더에 같은 이름의 파일이 있을 경우를 대비하기 위함
		// "년월일시분초.확장자" 형식으로 변경할 것임
		if(originalFileName != null) {
			//업로드된 파일이 있을 때만 파일명 바꾸기 실행함
			
			String renameFileName = FileNameChange.change(
					originalFileName, savePath, "yyyyMMddHHmmss");
			
			board.setBoardRenameFileName(renameFileName);			
		}  //업로드된 파일이 있다면...
		
		//모델 서비스 메소드로 전달하고 결과받기
		int result = new BoardService().insertOriginBoard(board);
		
		//받은 결과로 성공/실패 페이지 내보내기
		if(result > 0) {
			//서블릿에서 서블릿을 실행함
			response.sendRedirect("/first/blist?page=1");
		}else {
			view = request.getRequestDispatcher("views/common/error.jsp");
			request.setAttribute("message", "새 게시 원글 등록 실패.");
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
