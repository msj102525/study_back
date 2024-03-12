package notice.controller;

import java.io.File;
import java.io.IOException;
import java.sql.Date;

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
 * Servlet implementation class NoticeAdminUpdateServlet
 */
@WebServlet("/nupdate.admin")
public class NoticeAdminUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NoticeAdminUpdateServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 관리자용 : 공지글 수정 처리용 컨트롤러

		// 1. multipart 방식으로 인코딩되어서 전송왔는지 확인
		// 아니면 에러 페이지를 내보냄
		RequestDispatcher view = null;
		if (!ServletFileUpload.isMultipartContent(request)) {
			view = request.getRequestDispatcher("views/common/error.jsp");
			request.setAttribute("message", "form 의 enctype='multipart/form-data' 속성 누락됨.");
			view.forward(request, response);
		}

		// 2. 업로드할 파일의 용량제한 : 10메가바이트로 제한한다면
		int maxSize = 1024 * 1024 * 10;

		// 3. 업로드되는 파일의 저장 폴더 지정
		String savePath = request.getSession().getServletContext().getRealPath("/resources/notice_upfiles");

		// 4. request 를 MultipartRequest 로 변환해야 함
		// cos.jar 가 제공하는 클래스를 사용
		// MultipartRequest 객체가 생성되면,
		// 자동으로 폴더에 파일이 저장됨
		MultipartRequest mrequest = new MultipartRequest(request, savePath, maxSize, "UTF-8",
				new DefaultFileRenamePolicy());

		// 5. 데이터베이스 notice 테이블에 기록할 값 추출
		// mrequest 사용해야 함 (request 사용 못 함)
		Notice notice = new Notice();

		notice.setNoticeNo(Integer.parseInt(mrequest.getParameter("no")));
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
		
		// ------------------------------------------------------------------------------------------------------------

		// 이전 첨부파일에 대한 삭제여부 값 추출
		String deleteFlag = mrequest.getParameter("deleteFlag");		

		// 이전 첨부파일의 파일명 추출
		String originFilePath = mrequest.getParameter("ofile");
		String renameFilePath = mrequest.getParameter("rfile");
		if(originFilePath == null || originFilePath.equals("null")) {
			originFilePath = null;
			renameFilePath = null;
		}

		// 새로운 첨부파일명 추출
		String originalFileName = mrequest.getFilesystemName("upfile");

		// 첨부파일 확인 :
		// 원래 파일과 새로 업로드된 파일의 이름이 같고,
		// 파일 용량도 동일하면 원래 이름 그대로 notice 에 기록함
		// 업로드된 파일의 File 객체 만들기
		File newOriginFile = new File(savePath + "\\" + originalFileName);
		File originFile = new File(savePath + "\\" + renameFilePath);			

		if(originFilePath != null && 	originalFileName != null 
				&& originFilePath.equals(originalFileName) 
				&& newOriginFile.length() == originFile.length()) {			
			//원래 첨부파일이 있었는데 새로 첨부된 파일과 다르지 않은 경우
			notice.setOriginalFilePath(originFilePath);
			notice.setRenameFilePath(renameFilePath);
		}

		if(originFilePath != null && originalFileName == null) {			
			//원래 첨부파일이 있었는데 변경되지 않은 경우
			notice.setOriginalFilePath(originFilePath);
			notice.setRenameFilePath(renameFilePath);
		}	
		
		// 첨부파일이 없었는데 추가된 경우와
		// 첨부파일이 있었는데 변경된 경우 둘 다 해당됨
		if (originalFileName != null) {			
			// 새로 업로드된 파일이 있다면
			notice.setOriginalFilePath(originalFileName);

			String renameFileName = FileNameChange.change(
					originalFileName, savePath, "yyyyMMddHHmmss");

			notice.setRenameFilePath(renameFileName);
			
			//이전 첨부파일이 있었다면 삭제함
			if(originFilePath != null) {
				originFile.delete();
			}
		}
		
		if(originFilePath != null && deleteFlag != null && deleteFlag.equals("yes")) {			
			//원래 첨부파일이 있었는데 파일삭제가 선택된 경우
			notice.setOriginalFilePath(null);
			notice.setRenameFilePath(null);
			
			//폴더에 저장된 파일도 삭제함
			originFile.delete();
		}
		//System.out.println("nupdate.admin : " + notice);
		
		// 6. 서비스 메소드로 전달하고 결과받기
		int result = new NoticeService().updateNotice(notice);

		// 7. 받은 결과로 성공/실패 페이지 내보내기
		if (result > 0) {
			response.sendRedirect("nlist.admin");
		} else {
			view = request.getRequestDispatcher("views/common/error.jsp");
			request.setAttribute("message", notice.getNoticeNo() + "번 공지글 수정 실패!");
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
