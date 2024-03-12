<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="common.Paging" %>    
<%
	Paging paging = (Paging)request.getAttribute("paging");
	
	int startPage = paging.getStartPage();
	int endPage = paging.getEndPage();
	int maxPage = paging.getMaxPage();
	int currentPage = paging.getCurrentPage();
	int limit = paging.getLimit();
	
	String urlMapping = paging.getUrlMapping();
	
	String action = (String)request.getAttribute("action");
	String keyword = null, begin = null, end = null;

	if(action != null){
		if(action.equals("date")) {
			begin = (String)request.getAttribute("begin");
			end = (String)request.getAttribute("end");
		}else {
			keyword = (String)request.getAttribute("keyword");
		}
	}
%>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
<%-- 목록 페이징 처리 --%>
<% if(action == null){ %>
<div style="text-align:center;">
	<% if(currentPage <= 1){ %>
		[맨처음] &nbsp;
	<% }else{ //currentPage > 1 %>
		<a href="/first/<%= urlMapping %>?page=1">[맨처음]</a> &nbsp;
	<% } %>
	<%-- 이전 페이지 그룹으로 이동 --%>
	<% if((currentPage - 10) < startPage && (currentPage - 10) > 1){  //이전그룹이 있다면 %>
		<a href="/first/<%= urlMapping %>?page=<%= startPage - 10 %>">[이전그룹]</a> &nbsp;
	<% }else{ //이전그룹이 없다면 %>
		[이전그룹] &nbsp;
	<% } %>
	
	<%-- 현재 페이지가 속한 페이지그룹 숫자 출력 --%>
	<% for(int p = startPage; p <= endPage; p++){ 
			if(p == currentPage){
	%>
			<font color="blue" size="4"><b><%= p %></b></font>
	<%     }else{ %>
			<a href="/first/<%= urlMapping %>?page=<%= p %>"><%= p %></a>
	<% }} %>
	
	<%-- 다음 페이지 그룹으로 이동 --%>
	<% if((currentPage + 10) > endPage && (currentPage + 10) < maxPage){  //다음그룹이 있다면 %>
		<a href="/first/<%= urlMapping %>?page=<%= startPage + 10 %>">[다음그룹]</a> &nbsp;
	<% }else{ //다음그룹이 없다면 %>
		[다음그룹] &nbsp;
	<% } %>
	
	<% if(currentPage >= maxPage){ %>
		[맨끝] &nbsp;
	<% }else{ //currentPage < maxPage  %>
		<a href="/first/<%= urlMapping %>?page=<%= maxPage %>">[맨끝]</a> &nbsp;
	<% } %>
</div>
<% } %>

<%-- 검색(제목, 작성자, 내용) 페이징 처리 --%>
<% if(action != null && keyword != null){ %>
<div style="text-align:center;">
	<% if(currentPage <= 1){ %>
		[맨처음] &nbsp;
	<% }else{ //currentPage > 1 %>
		<a href="/first/<%= urlMapping %>?page=1&action=<%= action %>&keyword=<%= keyword %>">[맨처음]</a> &nbsp;
	<% } %>
	<%-- 이전 페이지 그룹으로 이동 --%>
	<% if((currentPage - 10) < startPage && (currentPage - 10) > 1){  //이전그룹이 있다면 %>
		<a href="/first/<%= urlMapping %>?page=<%= startPage - 10 %>&action=<%= action %>&keyword=<%= keyword %>">[이전그룹]</a> &nbsp;
	<% }else{ //이전그룹이 없다면 %>
		[이전그룹] &nbsp;
	<% } %>
	
	<%-- 현재 페이지가 속한 페이지그룹 숫자 출력 --%>
	<% for(int p = startPage; p <= endPage; p++){ 
			if(p == currentPage){
	%>
			<font color="blue" size="4"><b><%= p %></b></font>
	<%     }else{ %>
			<a href="/first/<%= urlMapping %>?page=<%= p %>&action=<%= action %>&keyword=<%= keyword %>"><%= p %></a>
	<% }} %>
	
	<%-- 다음 페이지 그룹으로 이동 --%>
	<% if((currentPage + 10) > endPage && (currentPage + 10) < maxPage){  //다음그룹이 있다면 %>
		<a href="/first/<%= urlMapping %>?page=<%= startPage + 10 %>&action=<%= action %>&keyword=<%= keyword %>">[다음그룹]</a> &nbsp;
	<% }else{ //다음그룹이 없다면 %>
		[다음그룹] &nbsp;
	<% } %>
	
	<% if(currentPage >= maxPage){ %>
		[맨끝] &nbsp;
	<% }else{ //currentPage < maxPage  %>
		<a href="/first/<%= urlMapping %>?page=<%= maxPage %>&action=<%= action %>&keyword=<%= keyword %>">[맨끝]</a> &nbsp;
	<% } %>
</div>
<% } %>

<%-- 검색(등록날짜) 페이징 처리 --%>
<% if(action != null && action.equals("date")){ %>
<div style="text-align:center;">
	<% if(currentPage <= 1){ %>
		[맨처음] &nbsp;
	<% }else{ //currentPage > 1 %>
		<a href="/first/<%= urlMapping %>?page=1&action=<%= action %>&begin=<%= begin %>&end=<%= end %>">[맨처음]</a> &nbsp;
	<% } %>
	<%-- 이전 페이지 그룹으로 이동 --%>
	<% if((currentPage - 10) < startPage && (currentPage - 10) > 1){  //이전그룹이 있다면 %>
		<a href="/first/<%= urlMapping %>?page=<%= startPage - 10 %>&action=<%= action %>&begin=<%= begin %>&end=<%= end %>">[이전그룹]</a> &nbsp;
	<% }else{ //이전그룹이 없다면 %>
		[이전그룹] &nbsp;
	<% } %>
	
	<%-- 현재 페이지가 속한 페이지그룹 숫자 출력 --%>
	<% for(int p = startPage; p <= endPage; p++){ 
			if(p == currentPage){
	%>
			<font color="blue" size="4"><b><%= p %></b></font>
	<%     }else{ %>
			<a href="/first/<%= urlMapping %>?page=<%= p %>&action=<%= action %>&begin=<%= begin %>&end=<%= end %>"><%= p %></a>
	<% }} %>
	
	<%-- 다음 페이지 그룹으로 이동 --%>
	<% if((currentPage + 10) > endPage && (currentPage + 10) < maxPage){  //다음그룹이 있다면 %>
		<a href="/first/<%= urlMapping %>?page=<%= startPage + 10 %>">[다음그룹]</a> &nbsp;
	<% }else{ //다음그룹이 없다면 %>
		[다음그룹] &nbsp;
	<% } %>
	
	<% if(currentPage >= maxPage){ %>
		[맨끝] &nbsp;
	<% }else{ //currentPage < maxPage  %>
		<a href="/first/<%= urlMapping %>?page=<%= maxPage %>&action=<%= action %>&begin=<%= begin %>&end=<%= end %>">[맨끝]</a> &nbsp;
	<% } %>
</div>
<% } %>

</body>
</html>