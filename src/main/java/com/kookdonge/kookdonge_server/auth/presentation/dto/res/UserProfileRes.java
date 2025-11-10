package com.kookdonge.kookdonge_server.auth.presentation.dto.res;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
@Schema(description = "사용자 프로필 응답")
public class UserProfileRes {

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;

    @Schema(description = "이메일", example = "user@kookmin.ac.kr")
    private String email;

    @Schema(description = "학번", example = "20211234")
    private String studentId;

    @Schema(description = "전화번호", example = "010-1234-5678")
    private String phoneNumber;

    @Schema(description = "학과", example = "소프트웨어학과")
    private String department;

    @Schema(description = "소속 동아리 ID(동아리장)",nullable = true)
    private Long clubId;
}