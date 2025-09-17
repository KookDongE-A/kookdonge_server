package com.kookdonge.kookdonge_server.auth.common;

import com.kookdonge.kookdonge_server.common.ExceptionCode;

public enum AuthExceptionCode implements ExceptionCode {
    ALREADY_REGISTERED_USER("이미 가입된 회원입니다.", 5001),
    USER_NOT_FOUND("사용자를 찾을 수 없습니다.", 5002),
    INVALID_TOKEN("유효하지 않은 토큰입니다.", 5003),
    NOT_FOUND_AUTH_HEADER("Authorization 헤더가 존재하지 않습니다.", 5004),
    INVALID_REFRESH_TOKEN("유효하지 않은 리프레시 토큰입니다.", 5005)
    ;

    private final String message;
    private final int code;

    AuthExceptionCode(String message, int code) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getCode() {
        return code;
    }
}
