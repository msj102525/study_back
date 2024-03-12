<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>first</title>
</head>
<body>
	<%-- page 지시자 태그에 isErrorPage 를 true 로 지정한 경우에만, jsp 내장객체인 exception 객체를 사용할 수 있음
	jsp 페이지에서 발생한 에러(예외)를 자동으로 전달받게 되어 있음
	 --%>
	 <%
	 	if(exception != null) { // 다른 jsp 페이지에서 에러가 넘어 왔다면
	 %>
	 <h3>jsp 페이지 오류 : <%= exception.getMessage() %></h3>
	 <%
	 	} else {
	 %>
	 <h3>Sevlet 오류 : <%= request.getAttribute("message") %></h3>	
	 <%
	 	}
	 %>
	 <a href="/first/index.jsp">시작페이지로 이동</a>
	 <hr>
	 <%@ include file="footer.jsp" %>
</body>
</html>