package com.kookdonge.kookdonge_server.questions_and_answers.presentation;

import com.kookdonge.kookdonge_server.common.dto.ResponseDTO;
import com.kookdonge.kookdonge_server.questions_and_answers.presentation.dto.req.QuestionCreateReq;
import com.kookdonge.kookdonge_server.questions_and_answers.presentation.dto.res.QuestionAnswerRes;
import com.kookdonge.kookdonge_server.questions_and_answers.service.QuestionAnswerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
public class QuestionAnswerPresentation {

    private final QuestionAnswerService questionAnswerService;

    @PostMapping("/{clubId}/questions")
    public ResponseDTO<QuestionAnswerRes> createQuestion(
            @PathVariable Long clubId,
            @Valid @RequestBody QuestionCreateReq request
    ) {
        QuestionAnswerRes response = questionAnswerService.createQuestion(clubId, request);
        return ResponseDTO.ok(response);
    }

    @GetMapping("/questions")
    public ResponseDTO<Page<QuestionAnswerRes>> getQuestions(
            @RequestParam Long club,
            @PageableDefault(size = 20, sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ) {
        Page<QuestionAnswerRes> questions = questionAnswerService.getQuestionsByClub(club, pageable);
        return ResponseDTO.ok(questions);
    }
}