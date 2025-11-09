package com.kookdonge.kookdonge_server.club.presentation.dto.req;

import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubCategory;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubType;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.RecruitmentStatus;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClubListReq {

    @Parameter(description = "동아리 카테고리 필터 (공연예술, 교양봉사, 전시예술, 종교, 구기레져, 체육무도, 학술)", example = "PERFORMING_ARTS,ACADEMIC")
    private List<ClubCategory> categories;

    @Parameter(description = "동아리 유형 (중앙동아리/과동아리)", example = "CENTRAL")
    private ClubType type;

    @Parameter(description = "모집 상태 필터", example = "RECRUITING")
    private List<RecruitmentStatus> recruitmentStatus;

    @Parameter(description = "대상 학년", example = "2")
    private Integer targetGraduate;

    @Parameter(description = "주간 활동 빈도", example = "2")
    private Integer weeklyActiveFrequency;

    @Parameter(description = "검색어 (동아리명, 설명 등)", example = "밴드")
    private String query;
}