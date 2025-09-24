package com.kookdonge.kookdonge_server.questions_and_answers.service;

import com.kookdonge.kookdonge_server.club.common.ClubExceptionCode;
import com.kookdonge.kookdonge_server.club.infra.jpa.repository.ClubRepository;
import com.kookdonge.kookdonge_server.common.exception.CustomException;
import com.kookdonge.kookdonge_server.questions_and_answers.infra.jpa.entity.QuestionsAndAnswersEntity;
import com.kookdonge.kookdonge_server.questions_and_answers.infra.jpa.repository.QuestionsAndAnswersRepository;
import com.kookdonge.kookdonge_server.questions_and_answers.presentation.dto.req.QuestionCreateReq;
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

        QuestionsAndAnswersEntity entity = QuestionsAndAnswersEntity.of(
                clubId,
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

    private void validateClubExists(Long clubId) {
        if (!clubRepository.existsById(clubId)) {
            throw new CustomException(ClubExceptionCode.CLUB_NOT_FOUND);
        }
    }
}