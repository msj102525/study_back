<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript"
	src="/first/resources/js/jquery-3.7.0.min.js"></script>
<script type="text/javascript">
	$(function() {
		$("#out").empty();
		$.ajax({
			url : "/first/kakaoMember",
			method : "post",
			dataType : "json",
			success : function(data) {
				var nickname = "";
				var email = "";
				$.each(data, function() {
					var profile = this['profile'];
					$.each(profile, function() {
						nickname = profile["nickname"];

					});
					email = this['email'];

				});
				$("#out").append(nickname + "(" + email + ")님 환영합니다.");
			},
			error : function() {
				alert("읽기실패");
			}
		});
	});
</script>
</head>
<body>
	<h1 id="btn">결과 보기</h1>
	<div id="out"></div>
	<button onclick="kakaoLogout();">로그아웃</button>
	
	<script src="https://developers.kakao.com/sdk/js/kakao.js"></script>
	<script type="text/javascript">
	function kakaoLogout() {
		//로그아웃 방법
		if (!Kakao.Auth.getAccessToken()) {
			console.log('Not logged in.');
			return;
		}

		Kakao.Auth.logout(function() {
			console.log(Kakao.Auth.getAccessToken());
		});
		
		//연결 끊기 방법
		Kakao.API.request({
			url : '/v1/user/unlink',
			success : function(response) {
				console.log(response);
			},
			fail : function(error) {
				console.log(error);
			},
		});
		
		Kakao.Auth.setAccessToken(null);		

	}
	</script>
</body>
</html>