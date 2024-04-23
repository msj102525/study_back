<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>boardWritePage</title>
</head>
<body>
	<h2>board : 게시글 등록 페이지</h2>
	<form action="/boards" method="post">
		글제목 : <input type="text" name="boardTitle"> <br>
		작성자 : <input type="text" name="boardWriter"> <br>
		내 용 : <textarea name="boardContent" cols="50" rows="5"></textarea>
		<input type="submit" value="게시글등록">
	</form>
</body>
</html>