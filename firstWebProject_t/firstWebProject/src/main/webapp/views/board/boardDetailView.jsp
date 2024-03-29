<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="board.model.vo.Board" %>
<%
	Board board = (Board)request.getAttribute("board");
	int currentPage = ((Integer)request.getAttribute("currentPage")).intValue();
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>first</title>
<script type="text/javascript">
function requestReply(){
	//댓글달기 요청 함수
	location.href = "/first/views/board/boardReplyForm.jsp?bnum=<%= board.getBoardNum() %>&page=<%= currentPage %>";
}

function requestDelete(){
	//게시글(원글, 댓글, 대댓글) 삭제 요청 함수
	location.href = "/first/bdelete?bnum=<%= board.getBoardNum() %>&lev=<%= board.getBoardLev() %>&rfile=<%= board.getBoardRenameFileName() %>";
}

function moveUpdatePage(){
	//게시글 (원글, 댓글, 대댓글) 수정 페이지로 이동 처리 함수
	location.href = "/first/bupview?bnum=<%= board.getBoardNum() %>&page=<%= currentPage %>";
}
</script>
</head>
<body>
<%@ include file="../common/menubar.jsp" %>
<hr>

<h1 align="center"><%= board.getBoardNum() %>번 게시글 상세보기</h1>
<br>

<table align="center" width="500" border="1" cellspacing="0" cellpadding="5">
	<tr>
		<th width="120">제 목</th>
		<td><%= board.getBoardTitle() %></td>
	</tr>
	<tr>
		<th width="120">작성자</th>
		<td><%= board.getBoardWriter() %></td>
	</tr>
	<tr>
		<th width="120">등록날짜</th>
		<td><%= board.getBoardDate() %></td>
	</tr>
	<tr>
		<th width="120">첨부파일</th>
		<td>
		<% if(board.getBoardOriginalFileName() != null){ %>
			<a href="/first/bfdown?ofile=<%= board.getBoardOriginalFileName() %>&rfile=<%= board.getBoardRenameFileName() %>">
			<%= board.getBoardOriginalFileName() %></a>
		<% }else{ %>
			&nbsp;
		<% } %>
		</td>
	</tr>
	<tr>
		<th width="120">내 용</th>
		<td><%= board.getBoardContent() %></td>
	</tr>
	<tr>
		<th colspan="2">
			<%-- 댓글달기 버튼은 로그인한 경우에만 보이게 함 --%>
			<% if(loginMember != null){ //로그인한 상태이면
					if(loginMember.getUserId().equals(board.getBoardWriter())){
						//로그인한 회원 아이디와 글작성자가 같다면 (본인이 올린 글이면)
			%>
				<button onclick="moveUpdatePage(); return false;">수정페이지로 이동</button> &nbsp;
				<button onclick="requestDelete(); return false;">글삭제</button> &nbsp;
			<%      }else if(loginMember.getAdminYN().equals("Y")){  //관리자이면 %>
				<button onclick="requestDelete(); return false;">글삭제</button> &nbsp;
			<%               if(board.getBoardLev() < 3){ %>
				<button onclick="requestReply(); return false;">댓글달기</button> &nbsp;
			<%               } %>
			<% 		 }else{ //로그인했는데 본인 글이 아닐 때 %>
				<%               if(board.getBoardLev() < 3){ %>
				<button onclick="requestReply(); return false;">댓글달기</button> &nbsp;
			<%               } %>
			<%       }
			     } %>
			 <button onclick="javascript:location.href='/first/blist?page=<%= currentPage %>';">목록</button> 
		</th>		
	</tr>
</table>
<br>

<hr>
<%@ include file="../common/footer.jsp" %>
</body>
</html>









