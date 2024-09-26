package com.fitcard.global.config.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitcard.global.error.BusinessException;
import com.fitcard.global.error.ErrorCode;
import com.fitcard.global.error.ErrorInfo;
import com.fitcard.global.response.Response;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ExceptionHandlerFilter extends OncePerRequestFilter {
    private final ObjectMapper objectMapper;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
        } catch (BusinessException e) {
            setErrorResponse(response, e);
        } catch (Exception e) {
            setUnexpectedErrorResponse(response, e);
        }
    }

    private void setErrorResponse(HttpServletResponse response, BusinessException e) {
        ErrorInfo errorInfo = new ErrorInfo(e.getErrorCode(), e.getMessage());
        log.error("error occurred in filter: {}", errorInfo.getTrackingId());
        log.error("error message: {}", errorInfo.getDetailMessage());
        e.printStackTrace();
        Response<ErrorInfo> errorResponse = Response.ERROR(e.getErrorCode(), e.getMessage());
        writeResponse(response, errorResponse, e.getErrorCode().getStatus());
    }
    private void setUnexpectedErrorResponse(HttpServletResponse response, Exception e) {
//        ErrorInfo errorResponse = new ErrorInfo(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
        Response<ErrorInfo> errorResponse = Response.ERROR(ErrorCode.INTERNAL_SERVER_ERROR, e.getMessage());
        log.error("unexpected error occurred in filter: {}", errorResponse.getData().getTrackingId());
        log.error("error message: {}", e.getMessage() + "\n" + response.toString());
        e.printStackTrace();
        writeResponse(response, errorResponse, 500);
    }
    private void writeResponse(HttpServletResponse response, Response<ErrorInfo> errorInfo, int status) {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.setStatus(status);
//        Response<ErrorInfo> errorResponse = Response.ERROR(e.getErrorCode(), e.getMessage());
        try {
            response.getWriter().write(objectMapper.writeValueAsString(errorInfo));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}