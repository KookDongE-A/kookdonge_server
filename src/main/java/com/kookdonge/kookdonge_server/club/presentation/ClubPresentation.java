package com.kookdonge.kookdonge_server.club.presentation;

import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubCategory;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubType;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.RecruitmentStatus;
import com.kookdonge.kookdonge_server.club.presentation.dto.res.ClubDetailRes;
import com.kookdonge.kookdonge_server.club.presentation.dto.res.ClubListRes;
import com.kookdonge.kookdonge_server.club.service.ClubService;
import com.kookdonge.kookdonge_server.common.dto.ResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "동아리", description = "동아리 조회 API")
@RestController
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
public class ClubPresentation {

    private final ClubService clubService;

    @GetMapping
    public ResponseDTO<Page<ClubListRes>> getClubList(
            @RequestParam(required = false) ClubCategory category,
            @RequestParam(required = false) ClubType type,
            @RequestParam(required = false) RecruitmentStatus recruitmentStatus,
            @RequestParam(required = false) Integer targetGraduate,
            @RequestParam(required = false) Integer weeklyActiveFrequency,
            @RequestParam(required = false) String query,
            @PageableDefault(size = 20, sort = "createdAt", direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ClubListRes> clubList = clubService.getClubList(category, type, recruitmentStatus, targetGraduate, weeklyActiveFrequency, query, pageable);
        return ResponseDTO.ok(clubList);
    }

    @GetMapping("/{clubId}")
    public ResponseDTO<ClubDetailRes> getClubDetail(@PathVariable Long clubId) {
        ClubDetailRes clubDetail = clubService.getClubDetail(clubId);
        return ResponseDTO.ok(clubDetail);
    }
}