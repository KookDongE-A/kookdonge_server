package com.kookdonge.kookdonge_server.questions_and_answers.presentation.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AnswerCreateReq {

    @NotBlank(message = "답변은 필수 입력 항목입니다.")
    private String answer;
}