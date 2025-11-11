package com.kookdonge.kookdonge_server.questions_and_answers.presentation;

import com.kookdonge.kookdonge_server.auth.service.annotation.LoginRequired;
import com.kookdonge.kookdonge_server.common.dto.ResponseDTO;
import com.kookdonge.kookdonge_server.questions_and_answers.presentation.dto.req.QuestionCreateReq;
import com.kookdonge.kookdonge_server.questions_and_answers.presentation.dto.req.AnswerCreateReq;
import com.kookdonge.kookdonge_server.questions_and_answers.presentation.dto.res.QuestionAnswerRes;
import com.kookdonge.kookdonge_server.questions_and_answers.service.QuestionAnswerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Tag(name = "질문답변")
@RestController
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
public class QuestionAnswerPresentation {

    private final QuestionAnswerService questionAnswerService;

    @Operation(summary = "질문 생성")
    @LoginRequired
    @PostMapping("/{clubId}/questions")
    public ResponseDTO<QuestionAnswerRes> createQuestion(
            @PathVariable Long clubId,
            @Valid @RequestBody QuestionCreateReq request
    ) {
        QuestionAnswerRes response = questionAnswerService.createQuestion(clubId, request);
        return ResponseDTO.ok(response);
    }

    @Operation(summary = "질문 목록 조회")
    @GetMapping("/questions")
    public ResponseDTO<Page<QuestionAnswerRes>> getQuestions(
            @RequestParam Long club,
            @PageableDefault(size = 20, sort = "createdAt", direction = Direction.DESC) Pageable pageable
    ) {
        Page<QuestionAnswerRes> questions = questionAnswerService.getQuestionsByClub(club, pageable);
        return ResponseDTO.ok(questions);
    }

    @Operation(summary = "답변 등록")
    @LoginRequired
    @PutMapping("/questions/{questionId}/answer")
    public ResponseDTO<QuestionAnswerRes> registerAnswer(
            @PathVariable Long questionId,
            @Valid @RequestBody AnswerCreateReq request
    ) {
        QuestionAnswerRes response = questionAnswerService.registerAnswer(questionId, request);
        return ResponseDTO.ok(response);
    }
}