package common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

public class FileDownload {
	public static void down(String savePath, String originalFileName, String renameFileName, 
			HttpServletResponse response) throws IOException {
		File readFile = new File(savePath + "\\" + renameFileName);
		BufferedInputStream bin = new BufferedInputStream(new FileInputStream(readFile));
		
		//요청한 클라이언트로 내보낼 출력 스트림 생성
		ServletOutputStream downOut = response.getOutputStream();
		
		//파일 다운을 위한 response 설정 처리
		response.setContentType("text/plain; charset=utf-8");
		//한글 파일명은 다운되는 컴퓨터의 os 문자셋에 맞추기함
		response.addHeader("Content-Disposition", "attachment; filename=\"" 
				+ new String(originalFileName.getBytes("utf-8"), "ISO-8859-1") + "\"");
		response.setContentLength((int)readFile.length());
		
		//파일 읽어서 내보내기
		int data = -1;
		while((data = bin.read()) != -1) {
			downOut.write(data);
			downOut.flush();
		}
		
		downOut.close();
		bin.close();
	}
}
