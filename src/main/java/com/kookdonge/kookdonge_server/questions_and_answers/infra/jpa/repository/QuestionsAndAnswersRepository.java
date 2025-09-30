package com.kookdonge.kookdonge_server.questions_and_answers.infra.jpa.repository;

import com.kookdonge.kookdonge_server.questions_and_answers.infra.jpa.entity.QuestionsAndAnswersEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionsAndAnswersRepository extends JpaRepository<QuestionsAndAnswersEntity, Long> {

    Page<QuestionsAndAnswersEntity> findByClubIdOrderByCreatedAtDesc(Long clubId, Pageable pageable);
}