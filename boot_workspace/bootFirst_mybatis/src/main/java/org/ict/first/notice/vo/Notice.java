package org.ict.first.notice.vo;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
//@RequiredArgsConstructor
//@EqualsAndHashCode(exclude="noticeNo")  //noticeNo 를 직렬화에서 제외시킴
public class Notice /*implements java.io.Serializable*/ {
	//private static final long serialVersionUID = 5875954839993211121L;
	
	//@NotNull
	private int noticeNo;
	//@NotNull
	private String noticeTitle;
	private java.sql.Date noticeDate;
	//@NotNull
	private String noticeWriter;
	private String noticeContent;
	private String originalFilePath;
	private String renameFilePath;
	private String importance;
	private java.sql.Date impEndDate;
	private int readCount;
	
}
