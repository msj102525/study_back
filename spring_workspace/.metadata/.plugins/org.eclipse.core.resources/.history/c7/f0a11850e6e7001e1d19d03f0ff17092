<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	//boardDetailView.jsp 가 보낸 파라메터(parameter) 값 꺼내기
	int boardNum = Integer.parseInt(request.getParameter("bnum"));  //원글번호
	int currentPage = Integer.parseInt(request.getParameter("page"));  //원글이 출력된 목록 페이지
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

<h1 align="center"><%= boardNum %>번글 댓글 등록 페이지</h1>
<br>

<form action="/first/breply" method="post">
	<input type="hidden" name="bnum" value="<%= boardNum %>">
	<input type="hidden" name="page" value="<%= currentPage %>">

<table align="center" width="500" border="1" cellspacing="0" cellpadding="5">
	<tr>
		<th>제 목</th>
		<td><input type="text" name="title" size="50"></td>
	</tr>
	<tr>
		<th>작성자</th>
		<td><input type="text" name="writer" readonly value="<%= loginMember.getUserId() %>"></td>
	</tr>	
	<tr>
		<th>내 용</th>
		<td><textarea rows="5" cols="50" name="content"></textarea></td>
	</tr>
	<tr>
		<th colspan="2">
			<input type="submit" value="등록하기"> &nbsp; 
			<input type="reset" value="작성취소"> &nbsp;
			<input type="button" value="목록" 
			onclick="javascript:location.href='/first/blist?page=<%= currentPage %>'; return false;">
		</th>		
	</tr>
</table>
</form>
<br>

<hr>
<%@ include file="../common/footer.jsp" %>
</body>
</html>