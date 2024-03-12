<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList, board.model.vo.Board, java.sql.Date" %>
<%
	ArrayList<Board> list = (ArrayList<Board>)request.getAttribute("list");

	int nowpage = 1;
	if(request.getAttribute("currentPage") != null){
		nowpage = ((Integer)request.getAttribute("currentPage")).intValue();
	}
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>first</title>
<style type="text/css">
fieldset#ss {
	width: 600px;
	position: relative;
	left: 450px;
}
form fieldset {
	width: 600px;	
}
form.sform {
	background: lightgray;
	width: 630px;
	position: relative;
	left: 400px;
	display: none;  /* 안 보이게 함 */
}
</style>
<script type="text/javascript" src="/first/resources/js/jquery-3.7.0.min.js"></script>
<script type="text/javascript">
$(function(){
	//input 태그의 name 이 item 의 값이 바뀌면(change) 작동되는 이벤트 핸들러 작성
	$('input[name=item]').on('change', function(){
		//여러 개의 태그 중에서 체크표시가 된 태그를 선택
		$('input[name=item]').each(function(index){
			//선택된 radio 순번대로 하나씩 checked 인지 확인함
			if($(this).is(':checked')){
				//체크 표시된 아이템에 대한 폼이 보여지게 처리함
				$('form.sform').eq(index).css('display', 'block');
			}else{
				//체크 표시 안된 아이템의 폼은 안 보이게 처리함
				$('form.sform').eq(index).css('display', 'none');
			}
		});  //each
	});  //on
});  //document ready

function showWriteForm(){
	location.href = "/first/views/board/boardWriteForm.jsp";
}
</script>
</head>
<body>
<%@ include file="../common/menubar.jsp" %>
<hr>
<h1 align="center">게시글 목록</h1>

<%-- 게시글 쓰기는 로그인한 회원만 가능함 --%>
<% if(loginMember != null){ %>
<div style="align:center;text-align:center;">
	<button onclick="showWriteForm();">글쓰기</button>
</div>
<% } %>
<br>

<center>
	<button onclick="javascript:location.href='/first/blist?page=1';">목록</button>
</center>
<br>

<%-- 항목별 검색 기능 추가 --%>
<fieldset id="ss">
	<legend>검색할 항목을 선택하세요.</legend>
	<input type="radio" name="item" id="title"> 제목 &nbsp;
	<input type="radio" name="item" id="writer"> 작성자 &nbsp;	
	<input type="radio" name="item" id="date"> 등록날짜 &nbsp;	
</fieldset>
<br>

<%-- 검색 항목별 값 입력 전송용 폼 만들기 --%>
<%-- 제목 검색 폼 --%>
<form id="titleform" class="sform" action="/first/bsearch" method="post">
	<input type="hidden" name="action" value="title">	
<fieldset>
	<legend>검색할 제목을 입력하세요.</legend>
	<input type="search" name="keyword"> &nbsp;
	<input type="submit" value="검색">
</fieldset>
</form>

<%-- 작성자 검색 폼 --%>
<form id="writerform" class="sform" action="/first/bsearch" method="post">
	<input type="hidden" name="action" value="writer">	
<fieldset>
	<legend>검색할 작성자를 입력하세요.</legend>
	<input type="search" name="keyword"> &nbsp;
	<input type="submit" value="검색">
</fieldset>
</form>

<%-- 등록날짜 검색 폼 --%>
<form id="dateform" class="sform" action="/first/bsearch" method="post">
	<input type="hidden" name="action" value="date">	
<fieldset>
	<legend>검색할 등록날짜를 선택하세요.</legend>
	<input type="date" name="begin"> ~ <input type="date" name="end"> &nbsp;
	<input type="submit" value="검색">
</fieldset>
</form>


<%-- 조회된 게시글 목록 출력 --%>
<table align="center" border="1" cellspacing="0" width="700">
	<tr>
		<th>번호</th>
		<th>제목</th>
		<th>작성자</th>
		<th>날짜</th>
		<th>첨부파일</th>
		<th>조회수</th>
	</tr>
	<% for(Board b : list){ %>
		<tr>
			<td align="center"><%= b.getBoardNum() %></td>
			<td>
			<% if(b.getBoardLev() == 2){  //원글의 댓글일 때 %>
			&nbsp; &nbsp; ▶ 
			<% }else if(b.getBoardLev() == 3){  //댓글의 댓글일 때 %>
			&nbsp; &nbsp; &nbsp; &nbsp; ▶▶
			<% } %>
			<a href="/first/bdetail?bnum=<%= b.getBoardNum() %>&page=<%= nowpage %>"><%= b.getBoardTitle() %></a>
			</td>
			<td align="center"><%= b.getBoardWriter() %></td>
			<td align="center"><%= b.getBoardDate() %></td>
			<td align="center">
			<% if(b.getBoardOriginalFileName() != null){ %>
				◎
			<% }else{ %>
				&nbsp;
			<% } %>
			</td>
			<td align="center"><%= b.getBoardReadCount() %></td>
		</tr>
	<% } %>
</table>
<br>

<%-- 페이징 처리 뷰 포함 처리 --%>
<%@ include file="../common/pagingView.jsp" %>

<hr>
<%@ include file="../common/footer.jsp" %>
</body>
</html>




