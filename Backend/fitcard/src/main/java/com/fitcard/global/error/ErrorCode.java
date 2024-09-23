package com.fitcard.global.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    /**
     * Error Code 400 : 잘못된 요청 401 : 권한 오류 403 : 서버가 허용하지 않은 웹페이지, 미디어 요청 404 : 존재하지 않는 정보에 대한 요청
     */

    //common
    NOT_FOUND_DATA(404, "C001", "해당하는 데이터를 찾을 수 없습니다."),
    BAD_REQUEST(400, "C002", "잘못된 요청입니다."),
    INTERNAL_SERVER_ERROR(500, "C003", "서버 에러입니다"),
    UNAUTHORIZED(403, "C004", "권한이 없습니다."),

    //auth
    UNAUTHORIZED_ACCESS(403, "A000", "접근 권한이 없습니다."),
    INVALID_TOKEN(404, "A001", "유효하지 않은 토큰 입니다"),
    INVALID_REFRESH_TOKEN(404, "A002", "RefreshToken 이 유효하지 않습니다"),
    EXPIRED_TOKEN(404, "A003", "만료된 토큰 입니다"),

    //member
    MEMBER_NOT_FOUND(404, "M001", "사용자를 찾을 수 없습니다."),
    MEMBER_UPDATE_FAILED(404, "M002", "사용자 정보 수정에 실패했습니다."),
    DUPLICATE_MEMBER(404, "M003", "이미 존재하는 ID 입니다."),
    INCORRECT_PASSWORD(400, "M004", "비밀번호가 틀렸습니다.");


    private final Integer status;
    private final String code;
    private final String message;
}