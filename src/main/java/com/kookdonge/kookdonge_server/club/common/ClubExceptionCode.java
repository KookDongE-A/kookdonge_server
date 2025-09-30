package com.kookdonge.kookdonge_server.club.common;

import com.kookdonge.kookdonge_server.common.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClubExceptionCode implements ExceptionCode {
    CLUB_NOT_FOUND("존재하지 않는 동아리입니다", 5000),
    CLUB_ACCESS_DENIED("동아리에 접근 권한이 없습니다", 5001),
    CLUB_ALREADY_EXISTS("이미 존재하는 동아리입니다", 5002),
    CLUB_RECRUITMENT_CLOSED("동아리 모집이 마감되었습니다", 5003)
    ;

    private final String message;
    private final int code;
}