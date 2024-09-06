package com.fitcard.global.response;

import com.fitcard.global.error.ErrorCode;
import com.fitcard.global.error.ErrorInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class Response<T> {

    private T data;
    private String message;
    private LocalDateTime timestamp;
    private HttpStatus statusCode;

    private Response(T data, String message, HttpStatus statusCode) {
        this.data = data;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.statusCode = statusCode;
    }

    @SuppressWarnings({"rawtypes", "unchecked"})
    public static Response SUCCESS() {
        return new Response(null, "", HttpStatus.OK);
    }

    public static <T> Response<T> SUCCESS(T data) {
        return new Response<>(data, "", HttpStatus.OK);
    }

    public static <T> Response<T> SUCCESS(T data, String message) {
        return new Response<>(data, message, HttpStatus.OK);
    }

    public static Response<ErrorInfo> ERROR(ErrorCode code) {
        return new Response<>(new ErrorInfo(code), code.getMessage(), HttpStatus.valueOf(code.getStatus()));
    }

    public static Response<ErrorInfo> ERROR(ErrorCode code, String detailMessage) {
        return new Response<>(new ErrorInfo(code, detailMessage), code.getMessage(), HttpStatus.valueOf(code.getStatus()));
    }
}