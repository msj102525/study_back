<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>first</title>
</head>
<body>
<h1>I'mport 결재 api 이용 테스트</h1>
<hr>
<ol>
<li>I'mport 개발가이드 접속 : https://www.iamport.kr/getstarted
<li>회원가입 : '대시보드' 클릭 (페이지 오른쪽 위)
<li>시스템 설정 : PG설정(일반결제 및 정기결제) 에서 PG사 선택, 저장<BR>
	[간편결재]카카오페이 선택하면, 가맹점코드 나타남 (복사해 둠 : 9810030929)<BR>
	내정보 : 가맹점 식별코드 복사해 둠 (imp04918071)<br>
	PG 추가 가능
<li>결제가 필요한 곳에 아임포트의 한줄 자바스크립트 라이브러리를 추가합니다.
아임포트는 jQuery 기반으로 동작하기 때문에 jQuery 1.0 이상이 반드시 설치되어 있어야 합니다.
아임포트 라이브러리 파일이 로드되면 window.IMP 변수에 접근이 가능합니다.

</ol>
<hr>
<H3><A HREF="./iamportPayment.jsp">결재테스트</A></H3>
</body>
</html>