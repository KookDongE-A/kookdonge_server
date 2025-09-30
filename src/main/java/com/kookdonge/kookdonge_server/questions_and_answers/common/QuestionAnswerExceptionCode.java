package com.kookdonge.kookdonge_server.questions_and_answers.common;

import com.kookdonge.kookdonge_server.common.exception.ExceptionCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum QuestionAnswerExceptionCode implements ExceptionCode {
    QUESTION_NOT_FOUND("존재하지 않는 질문입니다", 7000),
    ANSWER_ACCESS_DENIED("답변 권한이 없습니다", 7001);

    private final String message;
    private final int code;
}