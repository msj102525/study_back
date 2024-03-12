<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="notice.model.vo.Notice, java.util.ArrayList" %>    
<%
	ArrayList<Notice> list = (ArrayList<Notice>)request.getAttribute("list");
	int currentLimit = ((Integer)request.getAttribute("limit")).intValue();
	
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
	width: 650px;
	position: relative;
	left: 450px;
}
form fieldset {
	width: 600px;	
}
form.sform {
	background: lightgray;
	width: 650px;
	position: relative;
	left: 450px;
	display: none;  /* 안 보이게 함 */
}
</style>
<script type="text/javascript" src="/first/resources/js/jquery-3.7.0.min.js"></script>
<script type="text/javascript">
$(function(){
	var limit = "<%= currentLimit %>";
	document.getElementById("limit").value = limit;
	
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

function changeLimit(limit){
	//alert(limit);
	location.href = "/first/nlist?page=1&limit=" + limit;	
}
</script>
</head>
<body>
<%@ include file="../common/menubar.jsp" %>
<hr>
<h1 align="center">공지사항</h1>
<br>
<center>
	<button onclick="javascript:location.href='/first/nlist?page=1';">목록</button>
</center>
<br>

<%-- 항목별 검색 기능 추가 --%>
<fieldset id="ss">
	<legend>검색할 항목을 선택하세요.</legend>
	<input type="radio" name="item" id="title"> 제목 &nbsp;
	<input type="radio" name="item" id="content"> 내용 &nbsp;	
	<input type="radio" name="item" id="date"> 등록날짜 &nbsp;	
	<b style="color:blue;">출력할 목록 갯수를 선택하세요 : </b>
	<select id="limit" onchange="changeLimit(this.value);">		
		<option value="10">10개씩 출력</option>
		<option value="15">15개씩 출력</option>
		<option value="20">20개씩 출력</option>
	</select>	
</fieldset>
<br>

<%-- 검색 항목별 값 입력 전송용 폼 만들기 --%>
<%-- 제목 검색 폼 --%>
<form id="titleform" class="sform" action="/first/nsearch" method="post">
	<input type="hidden" name="action" value="title">
	<input type="hidden" name="limit" value="<%= currentLimit %>">	
<fieldset>
	<legend>검색할 제목을 입력하세요.</legend>
	<input type="search" name="keyword" size="50"> &nbsp;
	<input type="submit" value="검색">
</fieldset>
</form>

<%-- 작성자 검색 폼 --%>
<form id="contentform" class="sform" action="/first/nsearch" method="post">
	<input type="hidden" name="action" value="content">	
	<input type="hidden" name="limit" value="<%= currentLimit %>">
<fieldset>
	<legend>검색할 내용을 입력하세요.</legend>
	<input type="search" name="keyword" size="50"> &nbsp;
	<input type="submit" value="검색">
</fieldset>
</form>

<%-- 등록날짜 검색 폼 --%>
<form id="dateform" class="sform" action="/first/nsearch" method="post">
	<input type="hidden" name="action" value="date">	
	<input type="hidden" name="limit" value="<%= currentLimit %>">
<fieldset>
	<legend>검색할 등록날짜를 선택하세요.</legend>
	<input type="date" name="begin"> ~ <input type="date" name="end"> &nbsp;
	<input type="submit" value="검색">
</fieldset>
</form>

<%-- 조회된 공지사항 목록 출력 --%>
<table align="center" width="500" border="1" cellspacing="0" 
 cellpadding="0">
<tr>
	<th>번호</th>
	<th>제목</th>
	<th>작성자</th>
	<th>첨부파일</th>
	<th>날짜</th>
</tr>
<% for(Notice n : list){ %>
<tr>
	<td align="right"><%= n.getNoticeNo() %></td>
	<td><a href="/first/ndetail?no=<%= n.getNoticeNo() %>">
		<% if(n.getImportance().equals("Y")){ %>
				<img src="/first/resources/images/ico_notice.gif" width="15" height="15">
		<% } %> <%= n.getNoticeTitle() %></a>		
	</td>
	<td align="center"><%= n.getNoticeWriter() %></td>
	<td align="center">
		<% if(n.getOriginalFilePath() != null){ %>
			◎
		<% }else{ %>
			&nbsp;
		<% } %>
	</td>
	<td align="center"><%= n.getNoticeDate() %></td>
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





