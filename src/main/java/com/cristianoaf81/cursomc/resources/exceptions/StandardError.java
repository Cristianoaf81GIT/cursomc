package com.cristianoaf81.cursomc.resources.exceptions;

import java.io.Serializable;
import java.time.LocalDateTime;

public class StandardError implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer status;
	private String msg;
	private LocalDateTime dateTime;
	
	public StandardError(Integer status, String msg, LocalDateTime dateTime) {
		super();
		this.status = status;
		this.msg = msg;
		this.dateTime = dateTime;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public LocalDateTime getTimeStamp() {
		return dateTime;
	}
	public void setTimeStamp(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	
}
