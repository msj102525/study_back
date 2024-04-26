<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>  
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>  
    
<%-- <%@ page import="board.model.vo.Board %>    
<%
	Board board = (Board)request.getAttribute("board");
	int currentPage = ((Integer)request.getAttribute("currentPage")).intValue();
%> --%>   

<c:set var="currentPage" value="${ requestScope.currentPage }"/>
 
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>first</title>

<c:url var="replyf" value="/breplyform.do">
	<c:param name="bnum" value="${ requestScope.board.boardNum }"/>
	<c:param name="page" value="${ currentPage }" />
</c:url>

<c:url var="bdel" value="/bdelete.do">
	<c:param name="boardNum" value="${ requestScope.board.boardNum }"/>
	<c:param name="boardLev" value="${ requestScope.board.boardLev }" />
	<c:param name="boardRenameFileName" value="${ requestScope.board.boardRenameFileName }" />
</c:url>

<c:url var="bup" value="/bupview.do">
	<c:param name="bnum" value="${ requestScope.board.boardNum }"/>
	<c:param name="page" value="${ currentPage }" />
</c:url>

<script type="text/javascript">
function requestReply(){
	//댓글달기 요청 함수
	<%-- location.href = "/first/views/board/boardReplyForm.jsp?bnum=<%= board.getBoardNum() %>&page=<%= currentPage %>"; --%>
	location.href = "${ replyf }";
}

function requestDelete(){
	//게시글(원글, 댓글, 대댓글) 삭제 요청 함수
	<%-- location.href = "/first/bdelete?bnum=<%= board.getBoardNum() %>&lev=<%= board.getBoardLev() %>&rfile=<%= board.getBoardRenameFileName() %>"; --%>
	location.href = "${ bdel }";
}

function moveUpdatePage(){
	//게시글 (원글, 댓글, 대댓글) 수정 페이지로 이동 처리 함수
	<%-- location.href = "/first/bupview?bnum=<%= board.getBoardNum() %>&page=<%= currentPage %>"; --%>
	location.href = "${ bup }";
}
</script>
</head>
<body>
<%-- <%@ include file="../common/menubar.jsp" %> --%>
<c:import url="/WEB-INF/views/common/menubar.jsp" />
<hr>

<h1 align="center">${ requestScope.board.boardNum } 번 게시글 상세보기</h1>
<br>

<table align="center" width="500" border="1" cellspacing="0" cellpadding="5">
	<tr>
		<th width="120">제 목</th>
		<td>${ requestScope.board.boardTitle }</td>
	</tr>
	<tr>
		<th width="120">작성자</th>
		<td>${ requestScope.board.boardWriter }</td>
	</tr>
	<tr>
		<th width="120">등록날짜</th>
		<td><fmt:formatDate value="${ requestScope.board.boardDate }" pattern="yyyy-MM-dd" /></td>
	</tr>
	<tr>
		<th width="120">첨부파일</th>
		<td>
		<%-- <% if(board.getBoardOriginalFileName() != null){ %> --%>
		<c:if test="${ !empty requestScope.board.boardOriginalFileName }">
			<c:url var="bdown" value="bfdown.do">
				<c:param name="ofile" value="${ requestScope.board.boardOriginalFileName }"/>
				<c:param name="rfile" value="${ requestScope.board.boardRenameFileName }"/>
			</c:url>
			<%-- <a href="/first/bfdown?ofile=<%= board.getBoardOriginalFileName() %>&rfile=<%= board.getBoardRenameFileName() %>"> --%>
			<a href="${ bdown }">${ requestScope.board.boardOriginalFileName }</a>
		</c:if>
		<%-- <% }else{ %> --%>
		<c:if test="${ empty requestScope.board.boardOriginalFileName }">
			&nbsp;
		</c:if>
		<%-- <% } %> --%>
		</td>
	</tr>
	<tr>
		<th width="120">내 용</th>
		<td>${ requestScope.board.boardContent }</td>
	</tr>
	<tr>
		<th colspan="2">
			<%-- 댓글달기 버튼은 로그인한 경우에만 보이게 함 --%>			
			<%-- <% if(loginMember != null){ //로그인한 상태이면
					if(loginMember.getUserId().equals(board.getBoardWriter())){
						//로그인한 회원 아이디와 글작성자가 같다면 (본인이 올린 글이면)
			%> --%>
			<c:if test="${ !empty sessionScope.loginMember }">
				<c:if test="${ sessionScope.loginMember.userId eq requestScope.board.boardWriter }">
					<button onclick="moveUpdatePage(); return false;">수정페이지로 이동</button> &nbsp;
					<button onclick="requestDelete(); return false;">글삭제</button> &nbsp;
				</c:if> <%-- 본인 글일 때 --%>
			<%-- <%      }else if(loginMember.getAdminYN().equals("Y")){  //관리자이면 %> --%>
				<c:if test="${ (sessionScope.loginMember.adminYN eq 'Y') and (sessionScope.loginMember.userId ne requestScope.board.boardWriter) }">
					<button onclick="requestDelete(); return false;">글삭제</button> &nbsp;				
			<%-- <%               if(board.getBoardLev() < 3){ %> --%>
					<c:if test="${ requestScope.board.boardLev lt 3 }">
						<button onclick="requestReply(); return false;">댓글달기</button> &nbsp;
					</c:if>
				</c:if> <%-- 관리자일 때 --%>
			<%-- <%               } %>
			<% 		 }else{ //로그인했는데 본인 글이 아닐 때 %>
				<%               if(board.getBoardLev() < 3){ %> --%>
				<c:if test="${ (sessionScope.loginMember.adminYN eq 'N') and (sessionScope.loginMember.userId ne requestScope.board.boardWriter) }">
					<c:if test="${ requestScope.board.boardLev lt 3 }">
						<button onclick="requestReply(); return false;">댓글달기</button> &nbsp;
					</c:if>
				</c:if>  <%-- 본인글이 아닐 때 --%>
			</c:if> <%-- 로그인했을 때 --%>
			<%-- <%               } %>
			<%       }
			     } %> --%>
			     
			 <c:url var="bl" value="blist.do">
			 	<c:param name="page" value="${ currentPage }" />
			 </c:url>
			 <button onclick="javascript:location.href='${bl}';">목록</button>
			 <%-- <button onclick="javascript:location.href='/first/blist?page=<%= currentPage %>';">목록</button> --%> 
		</th>		
	</tr>
</table>
<br>

<hr>
<%-- <%@ include file="../common/footer.jsp" %> --%>
<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>









