package com.project.search.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.project.search.dto.ErrorDto;
import static com.project.search.util.exception.ErrorCode.INTERNAL_SERVER_ERROR;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler({ CustomException.class })
    protected ResponseEntity handleCustomException(CustomException ex) {
		ex.printStackTrace();
        return new ResponseEntity(new ErrorDto(ex.getErrorCode().getStatus(), ex.getErrorCode().getMessage()), HttpStatus.valueOf(ex.getErrorCode().getStatus()));
    }
	
	@ExceptionHandler({ Exception.class })
    protected ResponseEntity handleServerException(Exception ex) {
		ex.printStackTrace();
        return new ResponseEntity(new ErrorDto(INTERNAL_SERVER_ERROR.getStatus(), INTERNAL_SERVER_ERROR.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
