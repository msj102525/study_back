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
<script type="text/javascript">
function moveUpdatePage(){
	location.href = "/first/nmoveup.admin?noticeno=<%= notice.getNoticeNo() %>";
}

function requestDelete(){
	location.href = "/first/ndelete.admin?noticeno=<%= notice.getNoticeNo() %>&rfile=<%= notice.getRenameFilePath() %>";
}
</script>
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
<tr><th>중요도</th><td><%= notice.getImportance() %></td></tr>
<tr><th>중요도 설정 종료 날짜</th><td><%= notice.getImpEndDate() %></td></tr>
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
	<button onclick="moveUpdatePage(); return false;">수정페이지로 이동</button> &nbsp; 
	<button onclick="requestDelete(); return false;">삭제하기</button> &nbsp; 
	<button onclick="javascript:history.go(-1); return false;">목록</button>
</th></tr>
</table>
<hr>
<%@ include file="../common/footer.jsp" %>
</body>
</html>





