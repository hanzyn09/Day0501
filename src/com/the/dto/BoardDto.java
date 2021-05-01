package com.the.dto;

import java.sql.Timestamp;

public class BoardDto {
	private int no; //인덱스로 해도 되는데, 1번부터 시작. 자료형 확인
	private String subject; //대소문자 상관없음
	private String content;
	private int read_count;
	private String writer;
	private Timestamp created_date;
	
	public BoardDto() {
	}

	public BoardDto(int no, String subject, String content, int read_count, String writer, Timestamp created_date) {
		super();
		this.no = no;
		this.subject = subject;
		this.content = content;
		this.read_count = read_count;
		this.writer = writer;
		this.created_date = created_date;
	}
	
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getRead_count() {
		return read_count;
	}
	public void setRead_count(int read_count) {
		this.read_count = read_count;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Timestamp getCreated_date() {
		return created_date;
	}
	public void setCreated_date(Timestamp created_date) {
		this.created_date = created_date;
	}
}
