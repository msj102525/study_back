package example.controller;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;

/**
 * Servlet implementation class ThumbnailServliet1
 */
@WebServlet("/thumb2")
public class ThumbnailServliet2 extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ThumbnailServliet2() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 다중 이미지 파일 업로드시 썸네일 이미지 처리 컨트롤러 : 예제

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
		String savePath = request.getSession().getServletContext().getRealPath("/resources/exam_image_files");
		// request.getSession().getServletContext() => "/first" + 뒤에 하위 폴더 경로 추가함

		// 4. request 를 MultipartRequest 로 변환해야 함
		// MultipartRequest 클래스는 외부 라이브러리를 사용해야 함 : cos.jar 사용한 경우
		// MultipartRequest 객체가 생성되면, 자동으로 지정 폴더에 업로드된 파일이 저장됨
		MultipartRequest mrequest = new MultipartRequest(request, savePath, maxSize, "UTF-8",
				new DefaultFileRenamePolicy());

		// 5. 데이터베이스 board 테이블에 기록할 값 추출
		// mrequest 사용해야 함 (request 사용 못 함)//
		System.out.println(mrequest.getParameter("title"));
		System.out.println(mrequest.getParameter("writer"));
		System.out.println(mrequest.getParameter("content"));
		String[] fileNames = mrequest.getParameterValues("filenames");
		
		// 6. 업로드된 원본 파일이름 추출
		for(String fname : fileNames) {
			System.out.println(fname);
		}

		// 6. 업로드된 원본 파일이름 추출 : 마지막 파일명만 출력됨 
//		Enumeration fileList = mrequest.getFileNames();
//		Iterator fileIter = fileList.asIterator();
//		while (fileIter.hasNext()) {
//			String paramName = (String) fileIter.next();
//			System.out.println("폴더에 저장된 파일명 : " + mrequest.getFilesystemName(paramName));
//			System.out.println("전송온 원래 파일명 : " + mrequest.getOriginalFileName(paramName));
//		}
		
		//*******************************************************
		//업로드된 이미지 파일 중 첫번째 이미지를 썸네일 이미지로 만들기
		// Thumbnailator 라이브러리를 사용한 경우
		String thumbFileName = "thumb_" + fileNames[0];
		File thumbFile = new File(savePath + "\\" + thumbFileName);
		File firstImageFile = new File(savePath + "\\" + fileNames[0]);
		
		Thumbnails.of(firstImageFile).forceSize(50, 50).toFile(thumbFile);
		//일부만 추출할 경우
		//Thumbnails.of(firstImageFile).sourceRegion(Positions.TOP_CENTER).size(50, 50).toFile(thumbFile);
		//잘라서 추출할 경우
		//Thumbnails.of(firstImageFile).size(50, 50).crop(Positions.CENTER).toFile(thumbFile);

		// 모델 서비스 메소드로 전달하고 결과받기 (생략)

		// 받은 결과로 성공/실패 페이지 내보내기 (생략)
        view = request.getRequestDispatcher("views/example/resultThumb.jsp");
        request.setAttribute("image", thumbFileName);
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
