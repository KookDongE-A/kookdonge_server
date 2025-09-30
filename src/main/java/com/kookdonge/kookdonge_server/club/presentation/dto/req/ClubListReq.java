package com.kookdonge.kookdonge_server.club.presentation.dto.req;

import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubType;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.RecruitmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClubListReq {

    private List<String> categories;
    private ClubType type;
    private List<RecruitmentStatus> recruitmentStatus;
    private Integer targetGraduate;
    private Integer weeklyActiveFrequency;
    private String query; // 검색어 (동아리명, 설명 등)
}