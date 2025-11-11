package com.kookdonge.kookdonge_server.questions_and_answers.presentation.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionCreateReq {

    @NotBlank(message = "질문은 필수입니다")
    private String question;

    @NotBlank(message = "이름은 필수입니다")
    private String userName;
}