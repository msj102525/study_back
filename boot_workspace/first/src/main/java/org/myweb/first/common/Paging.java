package org.myweb.first.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Paging  {

	private int startRow;    //페이지에 출력할 시작행
	private int endRow;	//페이지에 출력할 끝행
	private int listCount;	//총 목록 갯수
	private int limit;		//한 페이지에 출력할 목록 갯수
	private int currentPage;	//출력할 현재 페이지
	private int maxPage;	//총 페이지 수(마지막 페이지)
	private int startPage;	//페이지 그룹의 시작값
	private int endPage;	//페이지 그룹의 끝값
	private String urlMapping;  //페이지 숫자 클릭시 요청할 url 저장용
	
	
}




