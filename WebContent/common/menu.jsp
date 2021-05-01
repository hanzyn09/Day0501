<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String root = request.getContextPath();	
	%>

	<nav>
		<a href="<%=root %>/">홈</a>
		<a href="<%=root %>/board/list">게시판</a>
		<a href="#">메뉴02</a>
		<a href="#">메뉴03</a>
	</nav>
	<hr>
</body>
</html>