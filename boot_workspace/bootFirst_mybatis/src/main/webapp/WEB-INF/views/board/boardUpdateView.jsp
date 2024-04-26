<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
     
<%-- <%@ page import="board.model.vo.Board"  %>    
<%
	Board board = (Board)request.getAttribute("board");
	int currentPage = ((Integer)request.getAttribute("currentPage")).intValue();
%>   --%>  

<c:set var="currentPage" value="${ requestScope.currentPage }"/>

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

<h1 align="center">${ requestScope.board.boardNum } 번 게시글 수정 페이지</h1>
<br>

<%-- 원글 수정 폼 : 첨부파일 수정 기능 포함 --%>
<%-- <% if(board.getBoardLev()  == 1){ %> --%>
<c:if test="${ requestScope.board.boardLev eq 1 }">
<!-- form 에서 입력값들과 파일을 함께 전송하려면 반드시 속성 추가해야 함 :  
	enctype="multipart/form-data"
-->
<form action="${ pageContext.servletContext.contextPath }/boriginupdate.do" method="post" enctype="multipart/form-data">
	<input type="hidden" name="boardNum" value="${ requestScope.board.boardNum }">
	<input type="hidden" name="page" value="${ currentPage }">
	<input type="hidden" name="boardOriginalFileName" value="${ requestScope.board.boardOriginalFileName }">
	<input type="hidden" name="boardRenameFileName" value="${ requestScope.board.boardRenameFileName }">

<table align="center" width="500" border="1" cellspacing="0" cellpadding="5">
	<tr>
		<th>제 목</th>
		<td><input type="text" name="boardTitle" size="50" value="${ requestScope.board.boardTitle }"></td>
	</tr>
	<tr>
		<th>작성자</th>
		<td><input type="text" name="boardWriter" readonly value="${ requestScope.board.boardWriter }"></td>
	</tr>
	<tr>
		<th>첨부파일</th>
		<td>
		<%-- <% if(board.getBoardOriginalFileName() != null){ %> --%>
		<c:if test="${ !empty requestScope.board.boardOriginalFileName }">
			${ requestScope.board.boardOriginalFileName } &nbsp; 
			<input type="checkbox" name="deleteFlag" value="yes"> 파일삭제 <br>
			변경 : <input type="file" name="upfile">
		</c:if>
		<%-- <% }else{ %> --%>
		<c:if test="${ empty requestScope.board.boardOriginalFileName }">
			첨부된 파일 없음 <br>
			추가 : <input type="file" name="upfile">
		</c:if>
		<%-- <% } %>		 --%>
		</td>
	</tr>
	<tr>
		<th>내 용</th>
		<td><textarea rows="5" cols="50" name="boardContent">${ requestScope.board.boardContent }</textarea></td>
	</tr>
	<tr>
		<th colspan="2">
			<input type="submit" value="수정하기"> &nbsp; 
			<input type="reset" value="수정취소"> &nbsp;
			<input type="button" value="이전페이지로 이동" 
			onclick="javascript:history.go(-1); return false;"> &nbsp;
			<input type="button" value="목록" 
			onclick="javascript:location.href='${ pageContext.servletContext.contextPath }/blist.do?page=${ currentPage }'; return false;">
		</th>		
	</tr>
</table>
</form>
<br>
</c:if>
<%-- <% } %> --%>

<%-- 댓글, 대댓글 수정 폼 --%>
<%-- <% if(board.getBoardLev()  > 1){ %> --%>
<c:if test="${ requestScope.board.boardLev gt 1 }">
<form action="${ pageContext.servletContext.contextPath }/breplyupdate.do" method="post">
	<input type="hidden" name="boardNum" value="${ requestScope.board.boardNum }">
	<input type="hidden" name="page" value="${ currentPage }"">
	
<table align="center" width="500" border="1" cellspacing="0" cellpadding="5">
	<tr>
		<th>제 목</th>
		<td><input type="text" name="boardTitle" size="50" value="${ requestScope.board.boardTitle }"></td>
	</tr>
	<tr>
		<th>작성자</th>
		<td><input type="text" name="boardWriter" readonly value="${ requestScope.board.boardWriter }"></td>
	</tr>	
	<tr>
		<th>내 용</th>
		<td><textarea rows="5" cols="50" name="boardContent">${ requestScope.board.boardContent }</textarea></td>
	</tr>
	<tr>
		<th colspan="2">
			<input type="submit" value="수정하기"> &nbsp; 
			<input type="reset" value="수정취소"> &nbsp;
			<input type="button" value="이전페이지로 이동" 
			onclick="javascript:history.go(-1); return false;"> &nbsp;
			<input type="button" value="목록" 
			onclick="javascript:location.href='${ pageContext.servletContext.contextPath }/blist.do?page=${ currentPage }'; return false;">
		</th>		
	</tr>
</table>
</form>
<br>
</c:if>
<%-- <% } %> --%>

<hr>
<%-- <%@ include file="../common/footer.jsp" %> --%>
<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>





