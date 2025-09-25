package com.kookdonge.kookdonge_server.feed.common;

import com.kookdonge.kookdonge_server.common.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum FeedExceptionCode implements ExceptionCode {
    FEED_NOT_FOUND("존재하지 않는 피드입니다", 6000),
    CLUB_FEED_NOT_FOUND("해당 클럽의 피드가 존재하지 않습니다", 6001),
    FEED_ACCESS_DENIED("피드에 접근 권한이 없습니다.", 6002),
    FILE_ACCESS_DENIED("파일 업로드 권한이 없습니다", 6003)
    ;

    private final String message;
    private final int code;
}
