<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>first</title>
</head>
<body>
<h1>지도에 여러 개의 마커 표시하기</h1>
	위도 : <span id="lat"></span>, 
	경도 : <span id="lng"></span> <br>
	
	<script type="text/javascript">
		var lat = 0;
		var lng = 0;
		
		//현재 위치 조회해 오기
		navigator.geolocation.getCurrentPosition(
				function(position) {
			lat = position.coords.latitude;
			lng = position.coords.longitude;
			
			document.getElementById("lat").innerHTML = lat;
			document.getElementById("lng").innerHTML = lng;
		});
		
	</script>
	<hr>
	
	<!-- 지도를 표시할 div 입니다 -->
	<div id="map" style="width:600px;height:400px;"></div>
	<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=10a4f5ddc6296d7f583750f16a795394"></script>
	<script>
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		mapOption = {
			center : new kakao.maps.LatLng(lat, lng), // 지도의 중심좌표
			level : 4
		// 지도의 확대 레벨
		};

		// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
		var map = new kakao.maps.Map(mapContainer, mapOption);

		//마커가 표시될 위치가 여러개인 경우 (서블릿으로 부터 응답온 정보로 적용)
		var str = '${ requestScope.sendJson }';
		console.log(typeof(str));  //string
		var json = JSON.parse(str);	//string --> json object
		
		var positions = [];
		
		for(var i in json.list){
			latitude = json.list[i].lat;
			longitude = json.list[i].lng;
			console.log(latitude + ", " + longitude);
			
			positions[i] = {
					title : decodeURIComponent(json.list[i].title).replace(/\+/gi, " "),
					latlng : new kakao.maps.LatLng(latitude, longitude)
			};			
		} //for in
		console.log(positions);
		
		// 마커 이미지의 이미지 주소입니다 (예 : "/first/resources/images/marker.png")
		var imageSrc = "https://t1.daumcdn.net/localimg/localimages/07/mapapidoc/markerStar.png";
		
		for (var i = 0; i < positions.length; i++) {
		    // 마커 이미지의 이미지 크기 입니다
		    var imageSize = new kakao.maps.Size(24, 35); 
		    
		    // 마커 이미지를 생성합니다    
		    var markerImage = new kakao.maps.MarkerImage(imageSrc, imageSize); 
		    
		    // 마커를 생성합니다
		    var marker = new kakao.maps.Marker({
		        map: map, // 마커를 표시할 지도
		        position: positions[i].latlng, // 마커를 표시할 위치
		        title : positions[i].title, // 마커의 타이틀, 마커에 마우스를 올리면 타이틀이 표시됩니다
		        image : markerImage // 마커 이미지 
		    });
		}
	</script>
</body>
</html>













