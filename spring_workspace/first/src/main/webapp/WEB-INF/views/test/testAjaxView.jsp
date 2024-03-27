<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>first</title>
<script type="text/javascript" src="${ pageContext.servletContext.contextPath }/resources/js/jquery-3.7.0.min.js"></script>
</head>
<body>
	<h1>jQuery 로 ajax 스프링에  사용 테스트 페이지</h1>
	<hr>
	
	<h2>1. 서버로 전송하는 값은 없고, 결과로 문자열을 받아서 출력 : get 방식</h2>
	<p id="p1" style="width:300px;height:50px;border:1px solid red;"></p>	
	<button id="test1">테스트1</button>
	
	<script type="text/javascript">
		$(() => {
			// jQuery("selector").method([args, .....]);
			// jQuery("selector").method([args, .....]).method().method();
			$("#test1").click(()=> {
				// 서버측 컨트롤러로 서비스 요청하고, 결과로 문자열을 받는 경우에는
				// $.get('요청url', {파라매터이름 : 전송값, ....}, function(리턴값받을변수){ 요청 성공시 리턴값 처리 });
				
				// $.get() 사용
				$.get("test1.do", function(data){
					$("#p1").text(data);
				})
				// $.ajax() 사용
				$.ajax({
					url: "test1.do",
					type: "get",
					// dataType: "text",  // 'text' 는 기본값이므로 생략해도 됨
					success: (data) => {
						// 태그 안의 값을 추가하는 방식
						$("#p1").html($("#p1").text() + "<br>" + data);
					}
				});
			});
		});
	</script>
	<hr>
	
	<h2>2. 서버로 전송하는 값 있고, 결과로 문자열을 받아서 출력 : post 방식</h2>
	이름 : <input type="text" id="name"><br>
	나이 : <input type="number" id="age"><br>
	<p id="p2" style="width:300px;height:50px;border:1px solid red;"></p>	
	<button id="test2">테스트2</button>
	
	<script type="text/javascript">
	$(() => {
	    $('#test2').click(() => {
	        // $.post()
	        $.post("test2.do", {
	                name: $("#name").val(),
	                age: $("#age").val()
	            },
	            function(data) {
	                $("#p2").text(data);
	            });
	        
	        // $.ajax()
	        $.ajax({
	            url: "test2.do",
	            type: "post",
	            data: {
	                name: $("#name").val(),
	                age: $("#age").val()
	            },
	            dataType: "text",
	            success: function(data) {
	                // 태그 안의 값을 추가하는 방식
	                $("#p2").html($("#p2").text() + "<br>" + data);

	                // 만약, 리턴값에 따라 선택 적용해야 한다면
	                if (data == "ok") {
	                    $("#p2").html($("#p2").text() + "<h5>" + data + "</h5>");
	                } else {
	                    alert("서버측 반환값 : " + data);
	                }
	            }
	        });
	    });
	});
	</script>
	<hr>
	
	<h2>3. 서버로 전송하는 값은 없고, 결과로 json 객체 하나를 리턴받아서 출력 : post 방식</h2>
	<p id="p3" style="width:300px;height:150px;border:1px solid red;"></p>	
	<button id="test3">테스트3</button>
	<script type="text/javascript">
		$(function(){
			
			$("#test3").click(function(){
				console.log("h1")
				$.ajax({
					url: "test3.do",
					type: "post",
					dataType: "json", // "json" 은 post 사용 권장함
					success: function(data){
						// json 객체 한 개를 받을때는 바로  출력할 수 있음
						console.log('json data : ' + data);
						
						// 응답온 값에 인코딩된 문자열은 자바스크립트 내장함수인
						// decodeURIConponent(응답값) 사용해서 반드시 디코딩처리 해야 함
						// 디코딩한 다음에 보면 공백이 '+	' 로 표기되면, '+' 를 " " 공백문자로 변경하도록 함
						$("#p3").html("<b>최신 신규 공지글</b><br>"
										+ '번호 : ' + data.noticeno
										+ "<br>제목 : " + decodeURIComponent(data.noticetitle).replace(/\+/g, " ")
										+ "<br>작성자 : " + data.noticewriter
										+ "<br>날짜 : " + data.noticedate
										+ "<br>내용 : " + decodeURIComponent(data.noticecontent).replace(/\+/g, " ")
										);
						
					},
					error: function(request, status, errorData){
						console.log("error code : " + request.status 
										+ "\nMessage : " + request.responseText
										+ "\nError : " + errorData);
					}
					
				});
			});
			
		});
	</script>
	
</body>
</html>