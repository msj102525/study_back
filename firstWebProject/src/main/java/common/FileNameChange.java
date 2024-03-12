package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;

public class FileNameChange {

	public static String change(String originalFileName, String savePath, 
			String formatStr) throws IOException {
		String renameFileName = null;
		
		//바꿀 파일명에 대한 문자열 만들기
		//게시글 등록 요청 시점의 날짜정보를 이용함
		SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
		
		//변경할 파일명 만들기
		renameFileName = sdf.format(new java.sql.Date(System.currentTimeMillis()));
		
		//원본 파일의 확장자를 추출해서, 바꿀 파일명에 붙여줌
		renameFileName += "." + originalFileName.substring(
								originalFileName.lastIndexOf(".") + 1);
		
		//저장 폴더에 있는 원본 파일의 파일명 바꾸기함 : java.io.File 클래스의 메소드 이용함
		File originFile = new File(savePath + "\\" + originalFileName);
		File renameFile = new File(savePath + "\\" + renameFileName);
		
		if(!originFile.renameTo(renameFile)) {
			//renameTo() 메소드가 실패한 경우 (false), 직접 수동으로 바꾸기함
			//원본 파일의 내용을 읽어서 리네임파일에 복사해 넣고, 끝나면 원본 파일을 삭제함
			
			FileInputStream fin = new FileInputStream(originFile);
			FileOutputStream fout = new FileOutputStream(renameFile);
			
			int data = -1;
			while((data = fin.read()) != -1) {
				fout.write(data);
			}
			
			fin.close();
			fout.close();
			originFile.delete();  //원본 파일 삭제함
		} //직접 이름바꾸기
				
		return renameFileName;
	}
}
