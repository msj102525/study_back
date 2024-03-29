<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>testGeolocation</title>
<script type="text/javascript" src="/first/resources/js/jquery-3.7.0.min.js"></script>
<script type="text/javascript" src="/first/resources/js/modernizr-custom.min.js"></script>
</head>
<body>
<p id="comment">Geolocation 지원 여부 확인</p>
<button id="btnGeolocation">Geolocation 지원여부 확인</button>
<script type="text/javascript">
	$(function(){
		$('#btnGeolocation').on('click', supportGeolocation);
		//$('#btnGeolocation').on('click', supportGeolocationByModernizr);
	});
	
	//지원여부 확인
	function supportGeolocation(){
		//alert(window.navigator.geolocation);
		//true / false 의 결과를 원할 경우
		alert(!!window.navigator.geolocation);
	}
	
	function supportGeolocationByModernizr(){
		alert(Modernizr.geolocation);
	}
</script>
<hr>
<p id="comment2">경/위도좌표로 위치정보 조회</p>
<button id="btnPosition">위치정보 확인</button>
<script type="text/javascript">
	//options 설정
	var options = {
			enablehighAccuracy : true,
			maximumAge : 6000,
			//항상 캐싱된 위치를 얻고자 할 때는 infinity 로 지정함
			timeout : 45000
	};
	var watcher = null;


	$(function(){
		$('#btnPosition').on('click', showPosition);		
	});  //document ready
	
	function showPosition(){
		if(window.navigator.geolocation){
			//navigator.geolocation.getCurrentPosition(successCallback[, errorCallback[, options]]);
			//navigator.geolocation.getCurrentPosition(getPosition);
			
			//움직이는 위치에 대해 계속 새 위치로 갱신하고자 할 때
			watcher = navigator.geolocation.watchPosition(getPosition, handleError, options);
		}else{
			alert("이 브라우저에서는 Geolocation 기능이 지원되지 않습니다.");
		}
	}
	
	//success callback 함수 : 위치 요청이 성공했을 때 실행됨
	//위치 요청에 대해 위치값이 리턴되었을 때 실행됨
	//리턴된 위치값을 받아야 함 : 메개변수가 필요함
	function getPosition(position){
		//매개변수 position 이 요청된 위치값을 받음.
		$('#comment2').html("위도 : " + position.coords.latitude 
				+ "<br>경도 : " + position.coords.longitude);
	
		//새 위치에 대한 갱신을 취소
		navigator.geolocation.clearWatch(watcher);
	}
	
	//error callback 함수 : 정해진 시간 안에 위치정보가 리턴되지 않았을 때 실행됨.
	function handleError(error){
		//에러 처리
		//alert("Error : " + error.code + ", " + error.message);
		//alert(error);
		
		//error code
		//code 1 : 사용자가 위치정보에 대한 접근을 막은 경우
		if(error.code == error.PERMISSION_DENIED){
			alert("사용자가 위치정보에 대한 접근을 막았음.");
		}
		//code 2 : 네트워크 또는 GPS 에 연결할 수 없는 경우
		else if(error.code == error.POSITION_UNAVAILABLE){
			alert("인터넷 네트워크 또는 GPS 에 연결할 수 없음.");
		}
		//code 3 : 사용자의 위치정보를 계산하는 데 시간이 초과된 경우
		else if(error.code == error.TIMEOUT){
			alert("위치정보 계산에 시간이 초과됨.");
		}
		//code 4 : 그외 알 수 없는 문제가 생긴 경우
		else if(error.code == error.UNKNOW_ERROR){
			alert("알 수 없는 오류 발생됨.")
		}
	}
</script>




</body>
</html>







