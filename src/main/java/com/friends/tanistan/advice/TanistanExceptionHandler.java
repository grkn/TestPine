package com.friends.tanistan.advice;

import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.friends.tanistan.controller.resource.ErrorResource;
import com.friends.tanistan.exception.NotFoundException;

@ControllerAdvice
public class TanistanExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ NotFoundException.class })
	@ResponseBody
	public ResponseEntity<ErrorResource> handleNotFound(Exception ex, WebRequest request) {
		return new ResponseEntity<ErrorResource>(((NotFoundException) ex).getErrorResource(), new HttpHeaders(),
				HttpStatus.NOT_FOUND);
	}

	// Latest Solution
	@ExceptionHandler({ Throwable.class })
	public ResponseEntity<Object> handleExceptionLatest(Exception ex, WebRequest request) {
		return new ResponseEntity<Object>("Exception occured. Server error", new HttpHeaders(),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
