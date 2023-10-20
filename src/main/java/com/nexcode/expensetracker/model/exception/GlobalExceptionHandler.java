package com.nexcode.expensetracker.model.exception;

import java.text.SimpleDateFormat;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
	
	@ExceptionHandler(value = {RuntimeException.class})
	public ResponseEntity<Object> RunTimeExceptionHandler(RuntimeException e) {

		throw e;
	}
	
//	@ExceptionHandler(value = {HttpMessageNotReadableException.class})
//	public ResponseEntity<?> HttpMessageNotReadableExceptionHandler(HttpMessageNotReadableException e, HandlerMethod handlerMethod) {
//		Class<?> ControllerName = handlerMethod.getMethod().getDeclaringClass();
//        String MethodName = handlerMethod.getMethod().getName();
//        
//        ApiException apiException = new ApiException();
//        apiException.setTimestamp(sdf.format(System.currentTimeMillis()));
//        apiException.setError(HttpStatus.BAD_REQUEST);
//        apiException.setMessage("Not acceptable format for enum type!");
//        apiException.setStatus(HttpStatus.BAD_REQUEST.value());
//        apiException.setControllerName(ControllerName.toString());
//        apiException.setServiceName(MethodName.toString());
//		
//        return new ResponseEntity<ApiException> (apiException, HttpStatus.BAD_REQUEST);
//	}	
}
