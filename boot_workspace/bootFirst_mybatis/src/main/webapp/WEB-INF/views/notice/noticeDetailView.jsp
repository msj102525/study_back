<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>      
<%-- <%@ page import="notice.model.vo.Notice" %>
<%
	Notice notice = (Notice)request.getAttribute("notice");	
%>  --%>   
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
<h2 align="center">${ notice.noticeNo } 번 공지글 상세보기</h2>
<br>
<table align="center" width="500" border="1" cellspacing="0" 
 cellpadding="5">
<tr><th>제 목</th><td>${ notice.noticeTitle }</td></tr>
<tr><th>작성자</th><td>${ notice.noticeWriter }</td></tr>
<tr><th>등록날짜</th><td><fmt:formatDate value="${ notice.noticeDate }" pattern="yyyy-MM-dd" /></td></tr>
<tr><th>첨부파일</th>
   <td>
   		<%-- <% if(notice.getOriginalFilePath() != null){ %> --%>
   		<c:if test="${ !empty notice.originalFilePath }">
   			<c:url var="nfd" value="/nfdown.do">
   				<c:param name="ofile" value="${notice.originalFilePath }" />
   				<c:param name="rfile" value="${notice.renameFilePath }" />
   			</c:url>
   			<a href="${ nfd }">${notice.originalFilePath }</a>
   		</c:if>
   		<%-- <% }else{ %> --%>
   			<c:if test="${ empty notice.originalFilePath }">&nbsp;	</c:if>
   		<%-- <% } %> --%>
   </td>
</tr>
<tr><th>내 용</th><td>${ notice.noticeContent }</td></tr>
<tr><th colspan="2">
	<button onclick="javascript:history.go(-1);">목록</button>
</th></tr>
</table>
<hr>
<%-- <%@ include file="../common/footer.jsp" %> --%>
<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>





