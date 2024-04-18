<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>test_jsp</title>
</head>
<body>
	<h1>Test Spring Boot with JSP</h1>
	<%-- <h2>${ name }</h2> --%>
	<h2>${ list.size() }</h2>
	<table>
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>내용</th>
		</tr>
	
		<c:forEach items="${ list }" var="b">
			<tr>
				<td>${ b.boardNum }</td>
				<td>${ b.boardTitle }</td>
				<td>${ b.boardWriter }</td>
				<td>${ b.boardContent }</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>