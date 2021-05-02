package com.the.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.the.dto.BoardDto;

/*
 * URI : /Day0501/board/list
 * URL : http://localhost:8080/Day0501/board/list
 * */


@WebServlet("/board/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		request.setCharacterEncoding("utf-8");
		//StringBuffer url = request.getRequestURL();
		System.out.println("URI : " + uri);
		//System.out.println("URL : " + url);
		
		String[] strs = uri.split("[/]"); 	//[]안에 해당되는 범위. 주소를 / 로 분할
		String key = strs[strs.length-1]; 	//마지막 문자열만 key 변수에 저장.
		String path = null;
	
		if(key.equals("list")) {
			Connection connection = null; 
			
			//드라이버 로딩 필수.
		    //tomcat 사이트에서 확인 가능하며 mysql과 oracle은 url과 드라이버의 형식이 다르다.
		    try {
				Class.forName("oracle.jdbc.OracleDriver");
				
				String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe" ; //tomcat, documentation에서 확인
			    String user = "the";
			    String password = "oracle";
			    
			    connection = DriverManager.getConnection(url, user, password); //예외처리 해줘야 함
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
			   	} //while 끝
			   	
			   	request.setAttribute("list", list); //request 속성을 통해, list를 list라는 이름으로 값을 가져오게 함
			   	
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try{
					if(connection != null) connection.close();
				} catch(Exception e ){
					e.getMessage();
				}
			}
		    	
			path ="/WEB-INF/views/board/list.jsp";
			
		} else if(key.equals("write")) {
			path ="/WEB-INF/views/board/write.jsp";
		} else if(key.equals("insert")) {
			//웹에서 정보 읽어오기
			String subject = request.getParameter("subject"); //숫자로 가져오려면 형변환 필요, 스프링은 자동 맵핑해준다.
			String content = request.getParameter("content"); //오타나면 입력되지않는다.
			String writer = "guest"; //회원가입 처리 시 자동으로 저장시킬 수 있다.
			//no값이 없다 : primary key 인데 어떻게 처리할 것인지?
			Connection connection =null;
			
			//DB에 저장 : DB에 접속해야하는데 중복 되므로 클래스로 분리해서 따로 뽑아낸다. (지금은 연습을 위해서 타이핑)
			try {
				Class.forName("oracle.jdbc.OracleDriver");
				String url = "jdbc:oracle:thin:@127.0.0.1:1521:xe" ; //tomcat, documentation에서 확인
			    String user = "the";
			    String password = "oracle";
			    
			   	connection = DriverManager.getConnection(url, user, password); //예외처리. 커넥션을 통해 sql설정.
			   	System.out.println("DB 접속 완료");
			   	
			   	String sql = "INSERT INTO board"
			   			+ "(no, subject, content, writer, created_date) " //띄어쓰기 유의
			   			+ "values"
			   			+ "(SEQ_BOARD.nextval, ?, ?, ?, sysdate)";
			   	PreparedStatement pstmt =  connection.prepareStatement(sql);
			   	pstmt.setString(1, subject); //db 인덱스는 1번부터 시작
			   	pstmt.setString(2, content); 
			   	pstmt.setString(3, writer); 
			   	
			   	//int n = pstmt.executeUpdate(); //몇개가 업데이트 되었는지 리턴
			   	pstmt.executeUpdate();
				
			} catch (ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			} finally {
				try{
					if(connection != null) connection.close();
				} catch(Exception e ){
					e.getMessage();
				}
			}
			
			//list로 요청
			response.sendRedirect("list");
			return; //이걸 넣어줘야 아래에서 path null 처리 안함
		} 
		
		
		if(path == null) {
			response.sendError(404); //에러코드 발생
			return;
		}
		else if(path != null) {
			//페이지 이동 두가지 방법 중 request 방식 이용
			request.getRequestDispatcher(path).forward(request, response); //여기로 이동한다.
		}
		
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
