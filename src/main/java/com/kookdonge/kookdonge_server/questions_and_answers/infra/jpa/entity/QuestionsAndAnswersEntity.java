package com.kookdonge.kookdonge_server.questions_and_answers.infra.jpa.entity;

import com.kookdonge.kookdonge_server.common.entity.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "questions_and_answers")
public class QuestionsAndAnswersEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "questions_and_answers_id")
    private Long questionsAndAnswersId;

    @NotNull
    @Column(name = "club_id", nullable = false)
    private Long clubId;

    @NotNull
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotBlank
    @Column(nullable = false)
    private String userName;

    @NotBlank
    @Column(nullable = false)
    private String question;

    @Column
    private String answer;

    public static QuestionsAndAnswersEntity of(Long clubId, Long userId, String userName, String question) {
        return new QuestionsAndAnswersEntity(null, clubId, userId, userName, question, null);
    }

    public void updateAnswer(String answer) {
        this.answer = answer;
    }
}