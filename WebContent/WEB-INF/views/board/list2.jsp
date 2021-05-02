<%@page import="java.util.Vector"%>
<%@page import="java.util.List"%>
<%@page import="com.the.dto.BoardDto"%>
<%@page import="java.sql.Timestamp"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.DriverManager"%>
<%@ page import="java.sql.Connection" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

    <%
    //드라이버 로딩 필수.
    //tomcat 사이트에서 확인 가능하며 mysql과 oracle은 url과 드라이버의 형식이 다르다.
    Class.forName("oracle.jdbc.OracleDriver");
    
    String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe" ; //tomcat, documentation에서 확인
    String user = "the";
    String password = "oracle";
    
   	Connection connection = DriverManager.getConnection(url, user, password); //예외처리 해줘야 함
   	System.out.println("DB 접속 완료");
   	
   	//쿼리를 실행할 문서 준비해서 쿼리 셋팅
   	String sql = "SELECT * FROM board"; //조회
   	PreparedStatement pstmt = connection.prepareStatement(sql);
    
   	//쿼리 실행
   	ResultSet rs = pstmt.executeQuery();
   	List<BoardDto> list = new Vector<>();
   	while(rs.next()){
   		int no = rs.getInt("no"); //인덱스로 해도 되는데, 1번부터 시작. 자료형 확인
   		String subject = rs.getString("subject"); //대소문자 상관없음
   		String content = rs.getString("content");
   		int read_count = rs.getInt("read_count");
   		String writer = rs.getString("writer");
   		Timestamp created_date = rs.getTimestamp("created_date");
   		
   		BoardDto dto = new BoardDto(no, subject, content, read_count, writer, created_date);
   	/*
   		BoardDto dto = new BoardDto(); //생성자 오버로딩 해도 됨
    		dto.setNo(no);
   		dto.setSubject(subject);
   		dto.setContent(content);
   		dto.setCreated_date(created_date);
   		dto.setRead_count(read_count);
   		dto.setWriter(writer); 
   	*/
   		list.add(dto);
   	}
    %>
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
	<%
		for(BoardDto dto :list){ 
	%>
			<tr>
			<td><%=dto.getNo() %></td>
			<td><%=dto.getSubject() %></td>
			<td><%=dto.getRead_count() %></td>
			<td><%=dto.getWriter() %></td>
			<td><%=dto.getCreated_date() %></td>
		</tr>
	<% 
		} 
	%>
	<!-- null 값은 null로 처리된다.. 자주 쓰이지 않는 형태 -->
	</table>
</body>
</html>