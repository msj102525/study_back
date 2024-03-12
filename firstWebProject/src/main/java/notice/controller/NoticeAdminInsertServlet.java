package notice.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import common.FileNameChange;
import notice.model.service.NoticeService;
import notice.model.vo.Notice;

/**
 * Servlet implementation class NoticeAdminInsertServlet
 */
@WebServlet("/ninsert.admin")
public class NoticeAdminInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeAdminInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 관리자용 공지사항 등록 처리용 컨트롤러
		// 파일업로드가 있는 전송인 경우임
		
		//1. multipart 방식으로 인코딩되어서 전송왔는지 확인
		//아니면 에러 페이지를 내보냄
		RequestDispatcher view = null;
		if(!ServletFileUpload.isMultipartContent(request)) {
			view = request.getRequestDispatcher(
					"views/common/error.jsp");
			request.setAttribute("message", 
				"form 의 enctype='multipart/form-data' 속성 누락됨.");
			view.forward(request, response);
		}
		
		//2. 업로드할 파일의 용량제한 : 10메가바이트로 제한한다면
		int maxSize = 1024 * 1024 * 10;
		
		//3. 업로드되는 파일의 저장 폴더 지정
		String savePath = request.getSession()
				.getServletContext().getRealPath(
						"/resources/notice_upfiles");
		
		//4. request 를 MultipartRequest 로 변환해야 함
		//cos.jar 가 제공하는 클래스를 사용
		//MultipartRequest 객체가 생성되면, 
		//자동으로 폴더에 파일이 저장됨
		MultipartRequest mrequest = new MultipartRequest(
				request, savePath, maxSize, "UTF-8", 
				new DefaultFileRenamePolicy());
		
		//5. 데이터베이스 notice 테이블에 기록할 값 추출
		//mrequest 사용해야 함 (request 사용 못 함)
		Notice notice = new Notice();
		
		notice.setNoticeTitle(mrequest.getParameter("title"));
		notice.setNoticeWriter(mrequest.getParameter("writer"));
		notice.setNoticeContent(mrequest.getParameter("content"));
		
		//*** 중요도 수정 여부 확인 처리 -----------------------------------------------------------------------
		String importance = mrequest.getParameter("importance");
		if(importance != null && importance.equals("Y")) {
			notice.setImportance(mrequest.getParameter("importance"));			
		}else {
			notice.setImportance("N");
		}
		
		notice.setImpEndDate(Date.valueOf(mrequest.getParameter("imp_end_date")));
		
		// ----------------------------------------------------------------------------------------------------------
		
		//업로드된 원본 파일이름 추출
		String originalFileName = mrequest.getFilesystemName("ofile");
		notice.setOriginalFilePath(originalFileName);
		
		//저장된 원본 파일의 이름 바꾸기 하려면...
		//저장 폴더에 같은 이름의 파일이 있을 경우를 대비하기 위함
		//"년월일시분초.확장자" 형식으로 변경해 봄
		if(originalFileName != null) {
			//업로드된 파일이 있을 때만 파일명 바꾸기함
			
			String renameFileName = FileNameChange.change(
					originalFileName, savePath, "yyyyMMddHHmmss");
			
			notice.setRenameFilePath(renameFileName);
		}  //업로드된 파일이 있다면...
		
		//6. 서비스 메소드로 전달하고 결과받기
		int result = new NoticeService().insertNotice(notice);
		
		//7. 받은 결과로 성공/실패 페이지 내보내기
		if(result > 0) {
			response.sendRedirect("nlist.admin");
		}else {
			view = request.getRequestDispatcher(
					"views/common/error.jsp");
			request.setAttribute("message", "새 공지글 등록 실패!");
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
