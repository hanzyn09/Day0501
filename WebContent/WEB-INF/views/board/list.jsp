<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="/common/menu.jsp"/>
	<a href="write">글쓰기</a>
	게시판입니다.
	<table>
		<tr>
			<td>글번호</td>
			<td>제목</td>
			<td>조회수</td>
			<td>작성자</td>
			<td>작성일</td>
		</tr>
		
		<!-- 여기에 데이터를 입력해주세요 -->
		<c:forEach var="dto" items="${list}">
			<tr>
				<td>${dto.no }</td>
				<td>${dto.subject }</td>
				<td>${dto.read_count }</td>
				<td>${dto.writer }</td>
				<td>${dto.created_date }</td>
			</tr>
		</c:forEach>

	</table>
</body>
</html>