<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<jsp:include page="/common/menu.jsp"/>
	<a href="list">목록보기</a>
	글쓰기페이지입니다.
	<form action="./insert" method="post">
		<p>
			<label>제목</label><br>
			<input type="text" name="subject" placeholder="제목을 입력해주세요">
		</p>
			<p>
			<label>내용입력</label><br>
			<textarea rows="5" cols="30" name="content"></textarea>
		</p>
		<p>
			<input type="submit" value="등록">
			<button type="reset">취소</button>
		</p>
		<p>
			<input id ="accept" type="checkbox" name="check"><label for="accept">동의</label>
		</p>
	</form>
</body>
</html>