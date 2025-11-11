package com.kookdonge.kookdonge_server.questions_and_answers.presentation.dto.res;

import com.kookdonge.kookdonge_server.questions_and_answers.infra.jpa.entity.QuestionsAndAnswersEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAnswerRes {

    private Long id;
    private LocalDateTime createdAt;
    private String question;
    private String answer;
    private Long userId;
    private String userName;

    public static QuestionAnswerRes of(QuestionsAndAnswersEntity entity) {
        return new QuestionAnswerRes(
                entity.getQuestionsAndAnswersId(),
                entity.getCreatedAt(),
                entity.getQuestion(),
                entity.getAnswer(),
                entity.getUserId(),
                entity.getUserName()
        );
    }
}