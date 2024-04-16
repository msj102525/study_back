<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>first</title>
</head>
<body>
<h1>Open API 사용 테스트 페이지</h1>
<HR>

<ol>
	<li><a href="${ pageContext.servletContext.contextPath }/moveGeo.do">testGeolocation</a></li>
	<li><a href="${ pageContext.servletContext.contextPath }/moveMap.do">카카오맵</a></li>
	<li><a href="${ pageContext.servletContext.contextPath }/moveKlogin.do">카카오 로그인</a></li>
	<li><a href="${ pageContext.servletContext.contextPath }/moveAddress.do">주소검색</a></li>
	<li><a href="${ pageContext.servletContext.contextPath }/movePay.do">결재 api</a></li>
	<li><a href="${ pageContext.servletContext.contextPath }/moveSocket.do">websocket</a></li>
	<li><a href="${ pageContext.servletContext.contextPath }/moveStorage.do">webstorage</a></li>
</ol>

</body>
</html>