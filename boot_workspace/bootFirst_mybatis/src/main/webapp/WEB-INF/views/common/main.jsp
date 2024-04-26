<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" errorPage="error.jsp"  %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>first</title>
<style type="text/css">
div.lineA {
	height: 100px;
	border: 1px solid gray;
	float: left;
	position: relative;
	left: 120px;
	margin: 5px;
	padding: 5px;
}
div#banner {
	width: 500px;
	padding: 0;
}
div#banner img {
	width: 450px;
	height: 80px;
	padding: 0;
	margin-top: 10px;
}
div#loginBox {
	width: 280px;
	font-size: 10pt;
	text-align: left;
	padding-left: 20px;
}
div#loginBox button {
	width: 250px;
	height: 35px;
	background-color: navy;
	color: white;
	margin-top: 10px;
	margin-bottom: 15px;
	font-size: 14pt;
	font-weight: bold;
}
table#toplist td, table#newnotice td {
	text-align: center;
}

</style>
<%-- jquery 파일 로드 --%>
<script type="text/javascript" src="${ pageContext.servletContext.contextPath }/resources/js/jquery-3.7.0.min.js"></script>
<script type="text/javascript">
function movePage(){
	//자바스크립트로 페이지 이동 또는 서블릿 컨트롤러 연결 요청시
	//location 내장객체의 href 속성을 사용함
	location.href = "${ pageContext.servletContext.contextPath }/loginPage.do";
}

$(function(){
	//최근 등록된 공지글 3개 전송받아서 출력 처리
	$.ajax({
		url: "${ pageContext.servletContext.contextPath }/ntop3.do",
		type: "post",
		dataType: "json",
		success: function(data){
			console.log("success : " + data);
			
			//object --> string
			var str = JSON.stringify(data);
			
			//string --> json
			var json = JSON.parse(str);
			
			values = "";			
			for(var i in json.nlist){
				values += "<tr><td>" + json.nlist[i].no 
						+ "</td><td><a href='${ pageContext.servletContext.contextPath }/ndetail.do?noticeno=" 
						+ json.nlist[i].no + "'>"
						+ decodeURIComponent(json.nlist[i].title).replace(/\+/gi, " ") 
						+ "</a></td><td>"
						+ json.nlist[i].date + "</td></tr>";
			}
			
			$('#newnotice').html($('#newnotice').html() + values);
			//$('#newnotice').append(values);
		},
		error: function(jqXHR, textStatus, errorThrown){
			console.log("error : " + jqXHR + ", " + textStatus + ", " + errorThrown);
		}
	});  //ajax
	
	
	//조회수 많은 인기 게시글 상위 3개 조회 출력 처리
	$.ajax({
		url: "${ pageContext.servletContext.contextPath }/btop3.do",
		type: "post",
		dataType: "json",
		success: function(data){
			console.log("success : " + data);
			
			//object --> string
			var str = JSON.stringify(data);
			
			//string --> json
			var json = JSON.parse(str);
			
			values = "";			
			for(var i in json.list){
				//로그인한 회원만 게시글 상세보기를 할 수 있게 한다면
				<c:if test="${ !empty sessionScope.loginMember }">
				values += "<tr><td>" + json.list[i].bnum 
						+ "</td><td><a href='${ pageContext.servletContext.contextPath }/bdetail.do?bnum=" 
						+ json.list[i].bnum + "'>"
						+ decodeURIComponent(json.list[i].btitle).replace(/\+/gi, " ") 
						+ "</a></td><td>"
						+ json.list[i].rcount + "</td></tr>";
				</c:if>
				<c:if test="${ empty sessionScope.loginMember }">
				values += "<tr><td>" + json.list[i].bnum 
						+ "</td><td>" 
						+ decodeURIComponent(json.list[i].btitle).replace(/\+/gi, " ") 
						+ "</td><td>"
						+ json.list[i].rcount + "</td></tr>";
				</c:if>
			}
			
			$('#toplist').html($('#toplist').html() + values);
			//$('#toplist').append(values);
		},
		error: function(jqXHR, textStatus, errorThrown){
			console.log("error : " + jqXHR + ", " + textStatus + ", " + errorThrown);
		}
	});  //ajax
	
});  //document ready
</script>
</head>
<body>
<h1>Spring Boot web Project : boot_first</h1>

<%-- 메뉴바 표시 --%>
<%-- <%@ include file="./views/common/menubar.jsp" %> --%>
<c:import url="/WEB-INF/views/common/menubar.jsp" />
<%-- 속성 url 은 브라우저에 표시되는 페이지 경로에 대한 url 임
	기본 웰컴페이지 출력 url 이 http://localhost:8080/first 이므로,
	기본 url 뒤에 표기될 페이지 경로를 입력하면 됨
	url="http://localhost:8888/first/WEB-INF/views/common/menubar.jsp"
	또는 기본 url 은 생략 가능함
	url="/WEB-INF/views/common/menubar.jsp"
 --%>
<hr>
<center>
	<div id="banner" class="lineA">
		<img src="${ pageContext.servletContext.contextPath }/resources/images/photo2.jpg">
	</div>
	<%-- 로그인하지 않은 상태일 때 --%>
	<%-- <% if(loginMember == null){ %> --%>
	<c:if test="${ empty sessionScope.loginMember }">
	<div id="loginBox" class="lineA">
		first 사이트 방문을 환영합니다.<br>
		<button onclick="movePage();">로그인 하세요.</button><br>
		<a>아이디 조회/비밀번호 조회</a> &nbsp;
		<a href="${ pageContext.servletContext.contextPath }/enrollPage.do">회원가입</a>
	</div>
	</c:if>
	<%-- <% }else{ //로그인했다면 %> --%>
	<c:if test="${ !empty sessionScope.loginMember }">
	<div id="loginBox" class="lineA">
		${ loginMember.userName } 님. &nbsp;
		<a href="${ pageContext.servletContext.contextPath }/logout.do">로그아웃</a> <br>
		<a>메일</a> &nbsp; <a>채팅</a> &nbsp; <a>쪽지</a><br>
		
		<c:url var="callMyInfo" value="/myinfo.do">
			<c:param name="userId" value="${ sessionScope.loginMember.userId }" />
		</c:url>
		<a href="${ callMyInfo }">My Page</a>			
	</div>
	</c:if>
	<%-- <% } %> --%>
</center>
<hr style="clear:both;">

<%-- 최근 등록된 공지글 3개 출력 : ajax --%>
<div style="float:left;border:1px solid navy;padding:5px;margin:5px;margin-left:150px;">
	<h4>새로운 공지사항</h4>
	<table id="newnotice" border="1" cellspacing="0" width="350">
		<tr><th>번호</th><th>제목</th><th>날짜</th></tr>
	</table>
</div>

<%-- 조회수 많은 인기게시글 3개 출력 : ajax --%>
<div style="float:left;border:1px solid navy;padding:5px;margin:5px;margin-left:50px;">
	<h4>인기 게시글</h4>
	<table id="toplist" border="1" cellspacing="0" width="350">
		<tr><th>번호</th><th>제목</th><th>조회수</th></tr>
	</table>
</div>

<hr style="clear:both;">
<%-- jsp 파일 안에 별도로 작성된 jsp, html 파일을 포함할 수 있다.
    주의 : 상대경로만 사용할 수 있음
 --%> 
<%-- <%@ include file="views/common/footer.jsp" %> --%>
<c:import url="/WEB-INF/views/common/footer.jsp" />
</body>
</html>