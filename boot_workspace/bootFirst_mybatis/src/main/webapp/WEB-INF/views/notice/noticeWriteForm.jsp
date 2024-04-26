<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>first</title>
</head>
<body>
<%-- <%@ include file="../common/menubar.jsp" %> --%>
<c:import url="/WEB-INF/views/common/menubar.jsp" />
<hr>
<h1 align="center">새 공지글 등록 페이지</h1>
<br>
<!-- form 에서 입력값들과 파일을 함께 전송하려면
반드시 속성을 추가해야 함 : enctype="multipart/form-data" -->
<form action="${ pageContext.servletContext.contextPath }/ninsert.do" method="post" enctype="multipart/form-data">
<table align="center" width="500" border="1" cellspacing="0" cellpadding="5">
<tr><th>제 목</th><td><input type="text" name="noticeTitle" size="50"></td></tr>
<tr><th>작성자</th>
	<td><input type="text" name="noticeWriter" readonly 
		value="${ sessionScope.loginMember.userId }"></td></tr>
<tr><th>중요도</th>
	<td><input type="checkbox" name="importance" value="Y"> 중요</td>
</tr>
<tr><th>중요도 지정 종료 날짜</th>
	<td><input type="date" name="impEndDate"></td>
</tr>			
<tr><th>첨부파일</th><td><input type="file" name="ofile"></td></tr>
<tr><th>내 용</th>
	<td><textarea rows="5" cols="50" name="noticeContent"></textarea></td></tr>
<tr><th colspan="2">
	<input type="submit" value="등록하기"> &nbsp; 
	<input type="reset" value="작성취소"> &nbsp;
	<input type="button" value="목록" 
		onclick="javascript:history.go(-1); return false;">
</th></tr>
</table>
</form>
<br>
<hr>
<%-- <%@ include file="../common/footer.jsp" %> --%>
<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>