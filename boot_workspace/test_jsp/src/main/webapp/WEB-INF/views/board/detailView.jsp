<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>detailView</title>
<script type="text/javascript" src="resources/js/jquery-3.7.0.min.js"></script>
<script type="text/javascript">
$(function(){
	$('#deleteBtn').on('click', function(){
		var bno = '${ board.boardNum}';
		
		$.ajax({
			type: "DELETE",
			url: "/boards/" + bno,
			contentType: "application/json; charset=UTF-8",
			success: function(){
				location.href = "/home";
			},
			error: function(xhr, status, error){
				alert("code : " + xhr.status + "\n"
						+ "message : " + xhr.responseText + "\n"
						+ "error : " + error);
			}
		});
	});
	
	$('#modifyBtn').on('click', function(){
		var boardObject = {
				boardNum: ${ board.boardNum },
				boardTitle: $('input[name=boardTitle]').val(),
				boardContent: $('textarea').text()
		}
		
		var bno = '${ board.boardNum }';
		
		$.ajax({
			type: "PUT",
			url: "/boards/" + bno,
			data: JSON.stringify(boardObject),
			contentType: "application/json; charset=UTF-8",
			success: function(){
				location.href = "/home";
			},
			error: function(xhr, status, error){
				alert("code : " + xhr.status + "\n"
						+ "message : " + xhr.responseText + "\n"
						+ "error : " + error);
			}
		});
	});
});
</script>
</head>
<body>
	<h2>${ board.boardNum } 번 게시글 내용 보기 및 수정 페이지</h2>
	  <form> <!-- form 의 전송방식은 GET | POST 만 가능함 -->        
		<input type="hidden" name="boardNum" value="${ board.boardNum }">
		글제목 : <input type="text" name="boardTitle" value="${ board.boardTitle }"> <br>
		작성자 : <input type="text" name="boardWriter" readonly value="${ board.boardWriter }"> <br>
		내 용 : <textarea name="boardContent" cols="50" rows="5">${ board.boardContent }</textarea> <br>
		<input type="button" id="modifyBtn" value="게시글수정">
	</form>	
	<br>
	<button id="deleteBtn">게시글 삭제</button>
</body>
</html>