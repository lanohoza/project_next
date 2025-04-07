package com.pajiniweb.oriachad_backend.exceptions;

import com.pajiniweb.oriachad_backend.domains.dtos.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> handleResourceNotFoundException(ResourceNotFoundException ex, WebRequest request) {
		LOGGER.error("Resource not found", ex);
		return new ResponseEntity(ErrorResponse.builder().message(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<?> handleBadRequestException(BadRequestException ex, WebRequest request) {
		LOGGER.error("Bad request", ex);
		return new ResponseEntity(ErrorResponse.builder().message(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(LoginFailedException.class)
	public ResponseEntity<?> handleLoginFailedException(LoginFailedException ex, WebRequest request) {
		LOGGER.warn("Resource not found", ex);
		return new ResponseEntity(ErrorResponse.builder().message(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(ExpiredJwtException.class)
	public ResponseEntity<?> handleExpiredJwtException(ExpiredJwtException ex, WebRequest request) {
		LOGGER.error("Expired Jwt Exception", ex);
		return new ResponseEntity(ErrorResponse.builder().message("Token is not valid").build(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<?> handleGlobalException(Exception ex, WebRequest request) {
		LOGGER.error("Internal server error", ex);
		return new ResponseEntity(ErrorResponse.builder().message(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<?> handleGlobalRunException(RuntimeException ex, WebRequest request) {
		LOGGER.error("Internal server error", ex);
		return new ResponseEntity(ErrorResponse.builder().message(ex.getMessage()).build(), HttpStatus.BAD_REQUEST);
	}
}
