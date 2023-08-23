package com.rtasalem.videoGameShopApi.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.rtasalem.videoGameShopApi.exception.ResourceNotFoundException;
import com.rtasalem.videoGameShopApi.exception.VideoGameTitleExistsException;


@RestControllerAdvice
public class VideoGameControllerAdvice {
	
	@ExceptionHandler(value = ResourceNotFoundException.class)
	public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException ex) {
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
	}

	@ExceptionHandler(value = VideoGameTitleExistsException.class)
	public ResponseEntity<String> handleBookTitleExistsException(VideoGameTitleExistsException ex) {
		return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
		List<ObjectError> errors = ex.getAllErrors();
		StringBuilder sb = new StringBuilder();
		errors.forEach(error -> sb.append(error.getDefaultMessage() + ", "));
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(sb.toString());
	}

}
