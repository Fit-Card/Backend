package com.fitcard.global.error;

import com.fitcard.global.response.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.authorization.AuthorizationDeniedException;
//import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler
    protected ResponseEntity<Response<ErrorInfo>> businessExceptionHandler(BusinessException e){
        log.error("Businessexception - code: {} / message: {} / class: {}", e.getErrorCode().getCode(), e.getErrorCode().getMessage(), e.getClass());
        return ResponseEntity.status(e.getErrorCode().getStatus()).body(Response.ERROR(e.getErrorCode(), e.getMessage()));
    }

//    @ExceptionHandler(AuthorizationDeniedException.class)
//    public ResponseEntity<Response<ErrorInfo>> handleAuthorizationDeniedException(AuthorizationDeniedException ex) {
//        log.error("AuthorizationDeniedException - message: {} / class: {}", ex.getMessage(), ex.getClass());
//        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Response.ERROR(ErrorCode.UNAUTHORIZED, "관리자만 접근할 수 있습니다. " + ex.getMessage()));
//    }

    @ExceptionHandler(NumberFormatException.class)
    protected ResponseEntity<Response<ErrorInfo>> handleNumberFormatException(Exception ex){
        log.error("NumberFormatException - message: {} / class: {}", ex.getMessage(), ex.getClass());
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.ERROR(ErrorCode.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Response<ErrorInfo>> handleException(Exception ex){
        log.error("Exception - message: {} / class: {}", ex.getMessage(), ex.getClass());
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Response.ERROR(ErrorCode.INTERNAL_SERVER_ERROR, ex.getMessage()));
    }

}