<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
<%
	String jsonStr = (String)request.getAttribute("sendjson");
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>mapTest</title>
<script type="text/javascript">
window.onload = function(){
	var str = '<%= jsonStr %>';
	console.log(typeof(str));  //string
	var json = JSON.parse(str);	
	
	document.getElementById("output").innerHTML = json.list + '<BR>';
	
	for(var i in json.list){
		document.getElementById("output").innerHTML += 
			decodeURIComponent(json.list[i].title).replace(/\+/gi, " ") + '<br>';
	}
};
</script>
</head>
<body>
<h1>테스트 출력 확인</h1>
<div id="output">

</div>
</body>
</html>