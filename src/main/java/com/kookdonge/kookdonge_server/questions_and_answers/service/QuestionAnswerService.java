package com.kookdonge.kookdonge_server.questions_and_answers.service;

import com.kookdonge.kookdonge_server.questions_and_answers.common.QuestionAnswerExceptionCode;
import com.kookdonge.kookdonge_server.club.common.ClubExceptionCode;
import com.kookdonge.kookdonge_server.club.infra.jpa.repository.ClubRepository;
import com.kookdonge.kookdonge_server.common.exception.CustomException;
import com.kookdonge.kookdonge_server.common.info.UserInfoStore;
import com.kookdonge.kookdonge_server.questions_and_answers.infra.jpa.entity.QuestionsAndAnswersEntity;
import com.kookdonge.kookdonge_server.questions_and_answers.infra.jpa.repository.QuestionsAndAnswersRepository;
import com.kookdonge.kookdonge_server.questions_and_answers.presentation.dto.req.QuestionCreateReq;
import com.kookdonge.kookdonge_server.questions_and_answers.presentation.dto.req.AnswerCreateReq;
import com.kookdonge.kookdonge_server.questions_and_answers.presentation.dto.res.QuestionAnswerRes;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class QuestionAnswerService {

    private final QuestionsAndAnswersRepository questionsAndAnswersRepository;
    private final ClubRepository clubRepository;

    @Transactional
    public QuestionAnswerRes createQuestion(Long clubId, QuestionCreateReq request) {
        validateClubExists(clubId);

        Long userId = UserInfoStore.getUserId();

        QuestionsAndAnswersEntity entity = QuestionsAndAnswersEntity.of(
                clubId,
                userId,
                request.getUserName(),
                request.getQuestion()
        );

        QuestionsAndAnswersEntity savedEntity = questionsAndAnswersRepository.save(entity);
        return QuestionAnswerRes.of(savedEntity);
    }

    public Page<QuestionAnswerRes> getQuestionsByClub(Long clubId, Pageable pageable) {
        validateClubExists(clubId);

        return questionsAndAnswersRepository.findByClubIdOrderByCreatedAtDesc(clubId, pageable)
                .map(QuestionAnswerRes::of);
    }

    @Transactional
    public QuestionAnswerRes registerAnswer(Long questionId, AnswerCreateReq request) {
        QuestionsAndAnswersEntity question = questionsAndAnswersRepository.findById(questionId)
                .orElseThrow(() -> new CustomException(QuestionAnswerExceptionCode.QUESTION_NOT_FOUND));

        Long userClubId = UserInfoStore.getClubId();
        if (userClubId == null || !userClubId.equals(question.getClubId())) {
            throw new CustomException(QuestionAnswerExceptionCode.ANSWER_ACCESS_DENIED);
        }

        question.updateAnswer(request.getAnswer());

        return QuestionAnswerRes.of(question);
    }

    private void validateClubExists(Long clubId) {
        if (!clubRepository.existsById(clubId)) {
            throw new CustomException(ClubExceptionCode.CLUB_NOT_FOUND);
        }
    }
}