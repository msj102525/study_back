<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" erroPage="views/common/error.jsp"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>first</title>
</head>
<body>
	<h1>firstWebProject : first</h1>
	<hr>
	
	<hr>
	<%-- jsp 파일 안에 외부에서 별도로 작성된 jsp, html 파일을 불러다 포함시켜 보여지게 할 수 있음 --%>
	<%-- 주의 : 상대경로만 사용할 수 있음 --%>
	<%@ include file="views/common/footer.jsp" %>
</body>
</html>