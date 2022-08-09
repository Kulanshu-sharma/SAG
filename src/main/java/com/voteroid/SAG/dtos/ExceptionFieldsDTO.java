package com.voteroid.SAG.dtos;

import java.time.LocalDateTime;

public class ExceptionFieldsDTO {
	
	private LocalDateTime timestamp;
	private String message;
	
	public ExceptionFieldsDTO(LocalDateTime timestamp, String message) {
		super();
		this.timestamp = timestamp;
		this.message = message;
	}
	
	public ExceptionFieldsDTO() {
		
	}
	
	public LocalDateTime getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
