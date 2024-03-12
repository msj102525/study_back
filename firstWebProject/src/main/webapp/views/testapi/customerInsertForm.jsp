<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>first</title>
<script type="text/javascript" src="/first/resources/js/jquery-3.7.0.min.js"></script>
<!-- jQuery와 Postcodify를 로딩한다. -->
<script src="//d1p7wdleee1q2z.cloudfront.net/post/search.min.js"></script>
<script>
	/*  검색 단추를 누르면 팝업 레이어가 열리도록 설정한다. */
	$(function(){
		$("#postcodify_search_button").postcodifyPopUp();
	});
</script>
				
<style>
	span.guide {display:none; font-size:12px; top:12px, right:10px;}
	span.ok{color:green;}
	span.error{color:red;}
</style>
</head>
<body>
	<%@ include file="../common/menubar.jsp" %> 	
	<hr style="clear:both">
	<h1 align="center">주문서</h1>
	
	<div class="outer" align="center">
		<form action="/first/cinsert.do" method="post" id="joinForm">
			<table width="500" cellspacing="5">				
				<tr>
					<td>* 이름</td>
					<td><input type="text" name="name" required></td>
				</tr>				
				<tr>
					<td>이메일</td>
					<td><input type="email" name="email"></td>
				</tr>
				<tr>
					<td>전화번호</td>
					<td><input type="tel" name="phone"></td>
				</tr>
				<tr>
					<td>우편번호</td>
					<td>
						<input type="text" name="post" class="postcodify_postcode5" size="6">
						<button type="button" id="postcodify_search_button">검색</button>
					</td>
				</tr>
				<tr>
					<td>도로명 주소</td>
					<td><input type="text" name="address1" class="postcodify_address"></td>
				</tr>
				<tr>
					<td>상세 주소</td>
					<td><input type="text" name="address2" class="postcodify_extra_info"></td>
				</tr>
				
				
				<tr>
					<td colspan="2" align="center">
						<input type="submit" value="주문하기">
						&nbsp;
						<input type="reset" value="취소하기">
					</td>
				</tr>
			</table>
		</form>
		<br>
		<br>
		<a href="/first/index.jsp">시작 페이지로 이동</a>
	</div>	
	
</body>
</html>