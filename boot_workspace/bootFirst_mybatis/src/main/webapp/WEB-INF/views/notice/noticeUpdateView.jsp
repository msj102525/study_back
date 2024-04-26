<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>    
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
<h1 align="center">
${ notice.noticeNo } 번 공지글 수정 페이지</h1>
<br>
<!-- form 에서 입력값들과 파일을 함께 전송하려면
반드시 속성을 추가해야 함 : enctype="multipart/form-data" -->
<form action="${ pageContext.servletContext.contextPath }/nupdate.do" method="post" enctype="multipart/form-data">

<input type="hidden" name="noticeNo" value="${ notice.noticeNo }">	
<input type="hidden" name="originalFilePath" value="${notice.originalFilePath }">
<input type="hidden" name="renameFilePath" value="${notice.renameFilePath }">

<table align="center" width="500" border="1" cellspacing="0" cellpadding="5">
<tr><th>제 목</th>
<td><input type="text" name="noticeTitle" size="50" 
	value="${ notice.noticeTitle }"></td></tr>
<tr><th>작성자</th>
	<td><input type="text" name="noticeWriter" readonly 
		value="${ sessionScope.loginMember.userId }"></td></tr>
<tr><th>중요도</th>
	<td>
		<%-- <% if(notice.getImportance() != null && notice.getImportance().equals("Y")){ %> --%>
		<c:if test="${ !empty notice.importance and notice.importance eq 'Y' }">
			<input type="checkbox" name="importance" value="Y" checked> 중요
		</c:if>
		<%-- <% }
		   if(notice.getImportance() != null && notice.getImportance().equals("N")){ 
		%> --%>
		<c:if test="${ !empty notice.importance and notice.importance eq 'N' }">
			<input type="checkbox" name="importance" value="Y"> 중요
		</c:if>
		<%-- <% } %> --%>
	</td>
</tr>
<tr><th>중요도 지정 종료 날짜</th>
	<td><input type="date" name="impEndDate" value="${ notice.impEndDate }"></td>
</tr>		
<tr>
	<th>첨부파일</th>
	<td>
		<%-- <% if(notice.getOriginalFilePath() != null){ %> --%>
   		<c:if test="${ !empty notice.originalFilePath }">
			${notice.originalFilePath } &nbsp; 
			<input type="checkbox" name="deleteFlag" value="yes"> 
			파일삭제 <br>
			변경 : <input type="file" name="upfile">
		</c:if>
		<%-- <% }else{ %> --%>
		<c:if test="${ empty notice.originalFilePath }">
			첨부된 파일 없음 <br>
			추가 : <input type="file" name="upfile">	
		</c:if>		
		<%-- <% } %> --%>
		
	</td>
</tr>
<tr><th>내 용</th>
	<td><textarea rows="5" cols="50" name="noticeContent">${ notice.noticeContent }</textarea></td></tr>
<tr><th colspan="2">
	<input type="submit" value="수정하기"> &nbsp; 
	<input type="reset" value="수정취소"> &nbsp;
	<input type="button" value="이전페이지로 이동" 
		onclick="javascript:history.go(-1); return false;"> &nbsp;
	<input type="button" value="목록" 
		onclick="javascript:location.href='${ pageContext.servletContext.contextPath }/nlist.do'; return false;">
</th></tr>
</table>
</form>
<br>
<hr>
<%-- <%@ include file="../common/footer.jsp" %> --%>
<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>