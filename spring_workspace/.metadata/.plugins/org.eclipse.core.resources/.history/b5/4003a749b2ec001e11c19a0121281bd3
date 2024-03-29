<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>testAjaxView</title>
<script type="text/javascript" src="${ pageContext.servletContext.contextPath }/resources/js/jquery-3.7.0.min.js"></script>
</head>
<body>
<h1>jQuery 로 ajax 스프링 적용 테스트</h1>
<hr>

<h2>1. 서버로 전송하는 값은 없고, 결과로 문자열을 받아서 출력 : get 방식</h2>
<p id="p1" style="width:300px;height:50px;border:1px solid red;"></p>
<button id="test1">테스트1</button>

<script type="text/javascript">
$(function(){
	//jQuery('selector').method([args, .... ]);
	//$('selector').method(..).method(..).method(..);
	$('#test1').click(function(){
		//서버측 컨트롤러로 서비스 요청하고, 결과로 문자열을 받는 경우에는
		//$.get('요청url', {전송이름 : 전송값}, function(data){ 요청 성공시 리턴값 받는 함수 })
		
		//get 사용
		$.get("test1.do", function(data){
			$('#p1').text(data);
		});
		
		//ajax 사용
		$.ajax({
			url: "test1.do",
			type: "get",
			//dataType: "text",   //"text" 는 기본값이므로 생략 가능함
			success: function(data){
				//태그 안의 값에 추가하는 방식
				$('#p1').html($('#p1').text() + "<br>" + data);
			}
		});  //ajax
	}); //click
});  //document.ready
</script>
<hr>

<h2>2. 서버로 전송하는 값은 있고, 결과로 문자열을 받아서 출력 : post 방식</h2>
이름 : <input type="text" id="name"> <br>
나이 : <input type="number" id="age"> <br>
<p id="p2" style="width:300px;height:50px;border:1px solid red;"></p>
<button id="test2">테스트2</button>

<script type="text/javascript">$(function(){
	
	$('#test2').click(function(){
		//서버측 컨트롤러로 서비스 요청하고, 결과로 문자열을 받는 경우에는
		//$.post('요청url', {전송이름 : 전송값}, function(data){ 요청 성공시 리턴값 받는 함수 })
		
		//post 사용
		$.post("test2.do", 
				{name: $('#name').val() , age: $('#age').val() }, 
				function(data){
					$('#p2').text(data);
		});
		
		//ajax 사용
		$.ajax({
			url: "test2.do",
			type: "post",
			data: {name: $('#name').val() , 
				  age: $('#age').val()},
			//dataType: "text",   //"text" 는 기본값이므로 생략 가능함
			success: function(data){
				//태그 안의 값에 추가하는 방식
				$('#p2').html($('#p2').text() + "<br>" + data);
				//반환값에 따라 선택 적용한다면
				if(data == "ok"){
					$('#p2').html("<h5>" + data + "</h5>");
				}else{
					alert("서버측 답변 : " + data);
				}
					
			}
		});  //ajax
	}); //click
});  //document.ready
</script>
<hr>

<h2>3. 서버로 전송하는 값은 없고, 결과로 json 객체 하나를 받아서 출력 : post 방식</h2>
<p id="p3" style="width:300px;height:150px;border:1px solid red;"></p>
<button id="test3">테스트3</button>

<script type="text/javascript">
$(function(){	
	$('#test3').click(function(){		
		$.ajax({
			url: "test3.do",
			type: "post",
			dataType: "json",   //"json" 는 post 로 지정함
			success: function(data){
				//json 객체 한 개를 받을때는 바로 출력할 수 있음
				console.log("json data : " + data);
				
				//응답온 값에 인코딩된 문자가 있으면, 자바스크립트가 제공하는
				//decodeURIComponent(응답값) 함수 사용해서 반드시 디코딩 처리해야 함
				//디코딩 결과에 공백문자가 '+'로 표기되면
				//replace('현재문자', '바꿀문자') 사용해서 '+' 를 공백문자로 변경 처리함
				$('#p3').html("<b>최신 신규 공지글</b><br>"
						+ "번호 : " + data.noticeno
						+ "<br>제목 : " 
						+ decodeURIComponent(data.noticetitle).replace(/\+/gi, ' ')
						+ "<br>작성자 : " + data.noticewriter
						+ "<br>날짜 : " + data.noticedate
						+ "<br>내용 : "
						+ decodeURIComponent(data.noticecontent).replace(/\+/gi, ' '));
			}, 
			error: function(request, status, errorData){
				console.log("error code : " + request.status
						+ "\nMessage : " + request.responseText
						+ "\nError : " + errorData);
			}
		});  //ajax
	}); //click
});  //document.ready
</script>
<hr>

<h2>4. 서버로 전송값 있고, 결과로 json 배열 받아서 출력</h2>
<label>검색 제목 키워드 입력 : <input type="search" id="keyword"></label> <br>
<div id="p4" style="width:400px;height:400px;border:1px solid red;">
	<table id="tblist" border="1" cellspacing="0">
		<tr bgcolor="gray">
			<th>번호</th>
			<th>제목</th>
			<th>작성자</th>
			<th>날짜</th>
		</tr>
	</table>
</div>
<button id="test4">테스트4</button>

<script type="text/javascript">
$(function(){
	$('#test4').on('click', function(){
		$.ajax({
			url: 'test4.do',
			type: 'post', //json 을 받을때는 post 로 지정함
			data: { keyword: $('#keyword').val() },
			dataType: "json",  //받는 값의 종류 지정 (기본 : "text")
			success: function(dataObj){
				//json 배열을 담은 객체를 리턴받은 경우임
				console.log('dataObj : ' + dataObj);  //[object, Object]
				
				//object => string
				var objStr = JSON.stringify(dataObj);
				//string => parsing : json object
				var jsonObj = JSON.parse(objStr);
				
				var output = $('#tblist').html();
				//json 객체 안의 list 를 하나씩 꺼내서 새로운 행으로 추가 처리
				for(var i in jsonObj.list){
					output += "<tr><td>" + jsonObj.list[i].noticeno
							+ "</td><td>" + decodeURIComponent(jsonObj.list[i].noticetitle).replace(/\+/gi, ' ')
							+ "</td><td>" + jsonObj.list[i].noticewriter
							+ "</td><td>" + jsonObj.list[i].noticedate
							+ "</td></tr>";
				}
				
				//테이블에 기록 처리
				$('#tblist').html(output);
			},
			error: function(request, status, errorData){
				console.log("error code : " + request.status
						+ "\nMessage : " + request.responseText
						+ "\nError : " + errorData);
			}
		});
	});  //on
});  //document.ready
</script>
<hr>

<h2>5. 서버로 json 객체를 보내기</h2>
<div>
	<fieldset>
		<legend>새 공지글 등록하세요.</legend>
		제목 : <input type="text" id="title"> <br>
		작성자 : <input type="text" id="writer" value="${ sessionScope.loginMember.userId }" readonly>
		<br>
		내용 : <textarea rows="5" cols="30" id="content"></textarea>
	</fieldset>
</div>
<button id="test5">테스트5</button>

<script type="text/javascript">
$(function(){
	$('#test5').on('click', function(){
		//자바스크립트에서 json 객체 만들기
		//var job = { name: '홍길동', age: 30}; 형식으로 만들 수 있음
		var job = new Object();
		job.title = $('#title').val();
		job.writer = document.getElementById('writer').value;
		job.content = $('#content').val();
		
		$.ajax({
			url: "test5.do",
			type: 'post',
			data: JSON.stringify(job),
			contentType: "application/json; charset=utf-8",
			success: function(result){
				alert("요청 성공 : " + result);
				
				//input 에 기록된 값 삭제함
				$('#title').val("");
				$('#content').val("");
			},
			error: function(request, status, errorData){
				console.log("error code : " + request.status
						+ "\nMessage : " + request.responseText
						+ "\nError : " + errorData);
			}
		}); //ajax		
		
	});  //on
});  //document.ready
</script>
<hr>

<h2>6. 서버로 json 배열 보내기</h2>
<div>
	<fieldset>
		<legend>공지글 여러 개 한번에 등록하기</legend>
		제목 : <input type="text" id="ntitle"> <br>
		작성자 : <input type="text" id="nwriter" value="${ sessionScope.loginMember.userId }" readonly>
		<br>
		내용 : <textarea rows="5" cols="30" id="ncontent"></textarea>
	</fieldset>
	<input type="button" id="addBtn" value="추가하기">
</div>
<p id="p6" style="width:400px;height:150px;border:1px solid red;"></p>
<button id="test6">테스트6</button>

<script type="text/javascript">
$(function(){
	//자바스크립트에서 배열 만들기
	//var jarr = new Array(5);  //index 이용할 수 있음
	//jarr[0] = {name: '홍길동', age: 30}; 저장 기록함
	
	//var jarr2 = new Array(); //index 없음, 스택(stack) 구조가 됨(LIFO : Last Input First Output)
	//저장 : push(), 꺼내기 : pop() 사용함
	//jarr2.push({name: '홍길순', age: 25});
	
	//배열 초기화
	/*
		var jarr3 = [{name: '홍길동', age: 30}, 
					{name: '홍길순', age: 27}, 
					{name: '이순신', age: 45}];
	*/
	
	var jarr = new Array();
	var pStr = $('#p6').html();
	
	//추가하기 버튼 클릭시 입력값들을 읽어서 json 객체를 만들고
	//p6 태그 영역에 json string 을 추가 기록 처리함
	$('#addBtn').on('click', function(){
		//자바스크립트에서 json 객체 만들기
		//var job = { name: '홍길동', age: 30}; 형식으로 만들 수 있음
		var job = new Object();
		job.title = $('#ntitle').val();
		job.writer = $('#nwriter').val();
		job.content = $('#ncontent').val();
		
		jarr.push(job);
		
		pStr += JSON.stringify(job);
		$('#p6').html(pStr + "<br>");
		
		//기존 기록된 input 의 값은 지우기
		$('#ntitle').val("");
		$('#ncontent').val("");
		
	});  //addBtn click
	
	$('#test6').on('click', function(){
		$.ajax({
			url: "test6.do",
			type: 'post',
			data: JSON.stringify(jarr),
			contentType: "application/json; charset=utf-8",
			success: function(result){
				alert("요청 성공 : " + result);				
			},
			error: function(request, status, errorData){
				console.log("error code : " + request.status
						+ "\nMessage : " + request.responseText
						+ "\nError : " + errorData);
			}
		}); //ajax		
		
	});  //test6 click
});  //document.ready
</script>
<hr>

<h2>7. Ajax 로 파일 업로드 처리 (form 을 전송)</h2>
<h3>jQuery 기반 ajax 파일업로드 form 전송</h3>
<form id="fileform">
	메세지 : <input type="text" name="message"> <br>
	첨부파일 : <input type="file" name="upfile"> <br>
	<input type="button" value="업로드" onclick="uploadFile();">
</form>

<h3>javascript 기반 ajax 파일업로드 form 전송</h3>
<form id="fileform2">
	메세지 : <input type="text" name="message"> <br>
	첨부파일 : <input type="file" name="upfile"> <br>
	<input type="button" value="업로드" onclick="uploadFile2();">
</form>
<hr>

<script type="text/javascript">
function uploadFile(){
	//jQuery ajax() 로 파일업로드용 form 전송 처리
	
	//body 의 form 태그 객체 생성함
	var form = $('#fileform')[0];  //인덱스 사용에 주의할 것
	//form 태그 안의 입력양식들의 값들을 담을 FormData 객체 생성함
	var formData = new FormData(form);
	
	$.ajax({
		url: "testFileUp.do",
		processData: false,  //multipart 전송은 false 로 지정
		contentType: false,  //multipart 전송은 false 로 지정
		type: 'post',
		data: formData,
		success: function(data, jqXHR, textStatus){
			alert("파일업로드 성공 : " + data);
		},
		error: function(request, status, errorData){
			console.log("error code : " + request.status
					+ "\nMessage : " + request.responseText
					+ "\nError : " + errorData);
		}
	});  //ajax
}

function uploadFile2(){
	//javascript ajax() 로 파일업로드용 form 전송 처리
	
	var form = document.getElementById("fileform2");
	var formData = new FormData(form);
	
	//브라우저에서 제공하는 ajax 를 위한 객체 생성
	var xhRequest;
	if(window.XMLHttpRequest){
		xhRequest = new XMLHttpRequest();
	}else{
		xhRequest = new ActiveXObject("Microsoft.XMLHTTP");
	}
	
	//ajax 요청
	//1. 요청 처리에 대한 상태코드가 변경되면, 작동할 내용을 미리 설정함
	xhRequest.onreadystatechange = function(){
		if(xhRequest.readyState == 4 && xhRequest.status == 200){
			alert(xhRequest.responseText);
			//요청이 성공하면 alert 창에 응답온 문자를 출력함
		}
	};
	
	//2. url 요청하고, 전송값 보내기함
	xhRequest.open("POST", "testFileUp.do", true);
	xhRequest.send(formData);
}
</script>
<hr>

<h2>8. ajax 로 파일다운로드</h2>
<h3>jQuery 기반 ajax 이용 파일다운로드</h3>
<a id="fdown" onclick="fileDown();">sample.txt</a>
<br><br>

<h3>javascript 기반 ajax 이용 파일다운로드</h3>
<a id="fdown2" onclick="fileDown2();">sample.txt</a>
<br><br>

<script type="text/javascript">
function fileDown(){
	//jQuery ajax 로 파일다운로드 처리
	//a 태그(다운받을 파일명)을 클릭하면 서버로 다운로드 요청함
	
	//a 태그에서 다운받을 파일명을 얻어옴
	var filename = $('#fdown').text();
	
	$.ajax({
		url: "filedown.do",
		type: "get",  //get 이 기본값이므로 생략 가능
		data: { "filename": filename },
		xhrFields: { responseType: 'blob' },  //response 데이터를 바이너리로 지정해야 함
		success: function(data){
			console.log("파일 다운로드 성공!");
			
			//응답온 파일 데이터를 Blob 객체로 만듦
			var blob = new Blob([data]);
			//클라이언트 쪽에 파일 저장 처리 : 다운로드
			if(navigator.msSaveBlob){
				return navigator.msSaveBlob(blob, filename);
			}else{
				var link = document.createElement('a');
				link.href = window.URL.createObjectURL(blob);
				link.download = filename;
				link.click();
			}
		},
		error: function(request, status, errorData){
			console.log("error code : " + request.status
					+ "\nMessage : " + request.responseText
					+ "\nError : " + errorData);
		}
	});
}

function fileDown2(){
	//javascript ajax 로 파일다운로드 처리
	//a 태그(다운받을 파일명)을 클릭하면 서버로 다운로드 요청함
	
	var filename = document.getElementById("fdown2").innerHTML;
	var filedownURL = "filedown.do";
	
	//브라우저에 ajax 처리용 객체가 제공된다면
	if(window.XMLHttpRequest || 'ActiveXObject' in window){
		var link = document.createElement('a');
		link.href = filedownURL + "?filename=" + filename;
		link.download = filename || filedownURL;
		link.click();
	}else{
		//브라우저에 ajax 처리용 객체가 제공되는 않는다면
		var _window = window.open(filedownURL, filename);
		_window.document.close();
		_window.document.execCommand("SaveAs", true, filename || filedownURL);
		_window.close();
	}
}
</script>

</body>
</html>














