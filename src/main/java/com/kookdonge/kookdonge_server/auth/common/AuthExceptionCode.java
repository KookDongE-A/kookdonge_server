package com.kookdonge.kookdonge_server.auth.common;

import com.kookdonge.kookdonge_server.common.ExceptionCode;

public enum AuthExceptionCode implements ExceptionCode {
    ALREADY_REGISTERED_USER("이미 가입된 회원입니다.", 5001);

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
