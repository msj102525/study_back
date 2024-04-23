<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>test_jsp</title>
<script type="text/javascript" src="resources/js/jquery-3.7.0.min.js"></script>
</head>
<body>
	<h1>Test Spring Boot with JSP</h1>
	<div>
		<button onclick="javascript:location.href='${ pageContext.servletContext.contextPath }/movepage';">게시글 등록 페이지로 이동</button>
	</div>
	<%-- <h2>${ name }</h2> --%>
	<h2>${ list.size() }</h2>
	<table border="1" cellspacing="0">
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>내용</th>
		</tr>
	
		<c:forEach items="${ list }" var="b">
			<tr>
				<td>${ b.boardNum }</td>
				<td><a href="/boards?bno=${ b.boardNum }">${ b.boardTitle }</a></td>
				
				<td>${ b.boardWriter }</td>
				<td>${ b.boardContent }</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>