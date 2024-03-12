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
<h2 align="center"><%= notice.getNoticeNo() %> 번 공지글 상세보기</h2>
<br>
<table align="center" width="500" border="1" cellspacing="0" 
 cellpadding="5">
<tr><th>제 목</th><td><%= notice.getNoticeTitle() %></td></tr>
<tr><th>작성자</th><td><%= notice.getNoticeWriter() %></td></tr>
<tr><th>등록날짜</th><td><%= notice.getNoticeDate() %></td></tr>
<tr><th>첨부파일</th>
   <td>
   		<% if(notice.getOriginalFilePath() != null){ %>
   			<a href="/first/nfdown?ofile=<%= notice.getOriginalFilePath() %>&rfile=<%= notice.getRenameFilePath() %>"><%= notice.getOriginalFilePath() %></a>
   		<% }else{ %>
   			&nbsp;
   		<% } %>
   </td>
</tr>
<tr><th>내 용</th><td><%= notice.getNoticeContent() %></td></tr>
<tr><th colspan="2">
	<button onclick="javascript:history.go(-1);">목록</button>
</th></tr>
</table>
<hr>
<%@ include file="../common/footer.jsp" %>
</body>
</html>





