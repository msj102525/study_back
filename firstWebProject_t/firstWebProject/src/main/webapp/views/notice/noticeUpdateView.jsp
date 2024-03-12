<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="notice.model.vo.Notice" %>
<%
	Notice notice = (Notice)request.getAttribute("notice");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>first</title>
</head>
<body>
<%@ include file="../common/menubar.jsp" %>
<hr>
<h1 align="center">
<%= notice.getNoticeNo() %>번 공지글 수정 페이지</h1>
<br>
<!-- form 에서 입력값들과 파일을 함께 전송하려면
반드시 속성을 추가해야 함 : enctype="multipart/form-data" -->
<form action="/first/nupdate.admin" method="post" 
	enctype="multipart/form-data">

<input type="hidden" name="no" value="<%= notice.getNoticeNo() %>">	
<input type="hidden" name="ofile" value="<%= notice.getOriginalFilePath() %>">
<input type="hidden" name="rfile" value="<%= notice.getRenameFilePath() %>">

<table align="center" width="500" border="1" cellspacing="0" cellpadding="5">
<tr><th>제 목</th>
<td><input type="text" name="title" size="50" 
	value="<%= notice.getNoticeTitle() %>"></td></tr>
<tr><th>작성자</th>
	<td><input type="text" name="writer" readonly 
		value="<%= loginMember.getUserId() %>"></td></tr>
<tr><th>중요도</th>
	<td>
		<% if(notice.getImportance() != null && notice.getImportance().equals("Y")){ %>
			<input type="checkbox" name="importance" value="Y" checked> 중요
		<% }
		   if(notice.getImportance() != null && notice.getImportance().equals("N")){ 
		%>
			<input type="checkbox" name="importance" value="Y"> 중요
		<% } %>
	</td>
</tr>
<tr><th>중요도 지정 종료 날짜</th>
	<td><input type="date" name="imp_end_date" value="<%= notice.getImpEndDate() %>"></td>
</tr>		
<tr>
	<th>첨부파일</th>
	<td>
		<% if(notice.getOriginalFilePath() != null){ %>
			<%= notice.getOriginalFilePath() %> &nbsp; 
			<input type="checkbox" name="deleteFlag" value="yes"> 
			파일삭제 <br>
			변경 : <input type="file" name="upfile">
		<% }else{ %>
			첨부된 파일 없음 <br>
			추가 : <input type="file" name="upfile">			
		<% } %>
		
	</td>
</tr>
<tr><th>내 용</th>
	<td><textarea rows="5" cols="50" name="content"><%= notice.getNoticeContent() %></textarea></td></tr>
<tr><th colspan="2">
	<input type="submit" value="수정하기"> &nbsp; 
	<input type="reset" value="수정취소"> &nbsp;
	<input type="button" value="이전페이지로 이동" 
		onclick="javascript:history.go(-1); return false;"> &nbsp;
	<input type="button" value="목록" 
		onclick="javascript:location.href='/first/nlist.admin'; return false;">
</th></tr>
</table>
</form>
<br>
<hr>
<%@ include file="../common/footer.jsp" %>
</body>
</html>