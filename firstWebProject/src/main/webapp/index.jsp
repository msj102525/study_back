<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8" errorPage="views/common/error.jsp"
	import="member.model.dto.User"%>
<%
User loginUser = (User) session.getAttribute("loginUser");
%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>first</title>
<style type="text/css">
.lineA {
	height: 100px;
	border: 1px solid gray;
	float: left;
	position: relative;
	left: 120px;
	margin: 5px;
	padding: 5px;
}

#banner {
	width: 500px;
	padding: 0;
}

#banner img {
	display: block;
	width: 450px;
	height: 80px;
	padding: 0;
	margin-top: 10px;
}

#loginBox {
	width: 280px;
	font-size: 10pt;
	text-align: left;
	padding-left: 20px;
}

#loginBox button {
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
<!-- jQuery 코드 불러옴 -->
<script type="text/javascript"
	src="/first/resources/js/jquery-3.7.1.min.js"></script>
<script type="text/javascript">
function movePage() {
	// 자바스크립트로 페이지 연결 또는 서블릿 컨트롤러 연결 요청시에는
	// location 객체의 href 속성을 사용함 => 상대, 절대경로 둘 다 사용 가능함
	location.href = "/first/views/member/loginPage.html";
}

// jquery document ready ----------------------------
$(()=>{
		// 최근 등록된 공지사항 글 3개 전송받아서 출력 처리
		// setInterval(function(){
		$.ajax({
			url: "/first/ntop3",
			type: "get",
			dataType: "json",
			success: function(data){
				console.log("success : " + data);
				
				// object --> string
				var str = JSON.stringify(data);
				
				// string --> json
				var json = JSON.parse(str);
				
				values = "";
				for(var i in json.nlist){
					values += "<tr><td>" + json.nlist[i].no + "</td><td>" 
									+ decodeURIComponent(json.nlist[i].title).replace(/\+/gi, " ")
									+ "</td><td>" + json.nlist[i].date + "</td></tr>"
				}
				
				$("#newnotice").html($("#newnotice").html() + values);
				
			},
			error: function(jqXHR, textStatus, errorThrown){
				console.log("error : " + jqXHR + ", " + textStatus + ", " + errorThrown);
			}
		}); // $.ajax() close
		
		$.ajax({
			url: "/first/btop3",
			type: "get",
			dataType: "json",
			success: function(data){
				console.log("success : " + data);
				
				// object --> string
				var str = JSON.stringify(data);
				
				// string --> json
				var json = JSON.parse(str);
				
				values = "";
				for(var i in json.blist){
					values += "<tr><td>" + json.blist[i].bnum + "</td><td>" 
									+ decodeURIComponent(json.blist[i].btitle).replace(/\+/gi, " ")
									+ "</td><td>" + json.blist[i].rcount + "</td></tr>"
				}
				
				$("#toplist").html($("#toplist").html() + values);
				// $("toplist").append(values)
				
			},
			error: function(jqXHR, textStatus, errorThrown){
				console.log("error : " + jqXHR + ", " + textStatus + ", " + errorThrown);
			}
		}); // $.ajax() close
		// }, 1000 * 60 * 3); // setInterval(함수, 밀리초);
		
		
}); // document ready

</script>
</head>
<body>
	<h1>firstWebProject : first</h1>
	<hr>
	<header>
		<div id="banner" class="lineA">
			<img src="/first/resources/images/photo2.jpg">
		</div>
		<%-- 로그인하지 않은 상태일 때 --%>
		<%
		if (loginUser == null) {
		%>
		<div id="loginBox" class="lineA">
			first 사이트 방문을 환영합니다. <br>
			<button onclick="movePage();">로그인 하세요.</button>
			<br> <a href="/first/views/member/loginPage.html">회원가입</a>
			<%-- 회원가입 클릭하면 회원가입페이지가 연결되어 출력되게 링크 설정함 --%>
		</div>
		<%
		} else {// 로그인 했을 때
		%>
		<div id="loginBox" class="lineA">
			<%=loginUser.getUserName()%>
			님 &nbsp; <a href="/first/logout">로그아웃</a> <br>
		</div>
		<%
		}
		%>
	</header>
	<hr style="clear: both">
	<section>
		<%-- 최근 등록된 공지사항 글 3개 출력 : ajax --%>
		<div
			style="float: left; border: 1px solid navy; padding: 5px; margin: 5px; margin-left: 150px">
			<h4>새로운 공지사항</h4>
			<table id="newnotice" border="1" cellspacing="0" width="350">
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>날짜</th>
				</tr>
			</table>
		</div>

		<%-- 조회수 많은 인기 게시글 3개 출력 : ajax --%>
		<div
			style="float: left; border: 1px solid navy; padding: 5px; margin: 5px; margin-left: 50px">
			<h4>인기 게시물</h4>
			<table id="toplist" border="1" cellspacing="0" width="350">
				<tr>
					<th>번호</th>
					<th>제목</th>
					<th>조회수</th>
				</tr>
			</table>
		</div>
	</section>

	<hr style="clear: both">
	<%-- jsp 파일 안에 외부에서 별도로 작성된 jsp, html 파일을 불러다 포함시켜 보여지게 할 수 있음 --%>
	<%-- 주의 : 상대경로만 사용할 수 있음 --%>
	<%@ include file="views/common/footer.jsp"%>
</body>
</html>