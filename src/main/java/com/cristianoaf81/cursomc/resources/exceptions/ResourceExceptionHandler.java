package com.cristianoaf81.cursomc.resources.exceptions;

import java.time.LocalDateTime;

import javax.servlet.http.HttpServletRequest;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.cristianoaf81.cursomc.services.exceptions.DataIntegrityException;
import com.cristianoaf81.cursomc.services.exceptions.ObjectNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(
			ObjectNotFoundException e, 
			HttpServletRequest req
	) {
		Integer status = HttpStatus.NOT_FOUND.value();
		String message = e.getMessage();
		LocalDateTime dateTime = LocalDateTime.now();
		StandardError err = new StandardError(status, message, dateTime);
		return ResponseEntity.status(status).body(err);
	}

	
	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandardError> dataIntegrityException(
		DataIntegrityException e,
		HttpServletRequest req
	) {
		Integer status = HttpStatus.BAD_REQUEST.value();
		String message = e.getMessage();
		LocalDateTime dateTime = LocalDateTime.now();
		StandardError err = new StandardError(status, message, dateTime);
		return ResponseEntity.status(status).body(err);
	}
}
