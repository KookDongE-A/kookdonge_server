package com.kookdonge.kookdonge_server.club.presentation;

import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubCategory;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubType;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.RecruitmentStatus;
import com.kookdonge.kookdonge_server.auth.service.annotation.LoginRequired;
import com.kookdonge.kookdonge_server.club.presentation.dto.req.ClubListReq;
import com.kookdonge.kookdonge_server.club.presentation.dto.res.ClubDetailRes;
import com.kookdonge.kookdonge_server.club.presentation.dto.res.ClubLikeRes;
import com.kookdonge.kookdonge_server.club.presentation.dto.res.ClubListRes;
import com.kookdonge.kookdonge_server.club.service.ClubLikeService;
import com.kookdonge.kookdonge_server.club.service.ClubService;
import com.kookdonge.kookdonge_server.common.dto.ResponseDTO;
import io.swagger.v3.oas.annotations.tags.Tag;
import com.kookdonge.kookdonge_server.common.info.UserInfoStore;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "동아리", description = "동아리 조회 API")
@Tag(name = "동아리")
@RestController
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
public class ClubPresentation {

    private final ClubService clubService;
    private final ClubLikeService clubLikeService;

    @Operation(summary = "동아리 목록 조회")
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

    @Operation(summary = "동아리 상세 조회")
    @GetMapping("/{clubId}")
    public ResponseDTO<ClubDetailRes> getClubDetail(
            @PathVariable Long clubId,
            @RequestParam Long userId
    ) {
        ClubDetailRes clubDetail = clubService.getClubDetail(clubId, userId);
        return ResponseDTO.ok(clubDetail);
    }

    @GetMapping("/top/weekly-view")
    public ResponseDTO<Page<ClubListRes>> getTopClubsByWeeklyView(
            @PageableDefault(size = 10) Pageable pageable
    ) {
        Page<ClubListRes> topClubs = clubService.getTopClubsByWeeklyView(pageable);
        return ResponseDTO.ok(topClubs);
    }

    @GetMapping("/top/weekly-like")
    public ResponseDTO<Page<ClubListRes>> getTopClubsByWeeklyLike(
            @PageableDefault(size = 10) Pageable pageable
    ) {
        Page<ClubListRes> topClubs = clubService.getTopClubsByWeeklyLike(pageable);
        return ResponseDTO.ok(topClubs);
    }

    @Operation(summary = "동아리 좋아요")
    @PostMapping("/{clubId}/like")
    @LoginRequired
    public ResponseDTO<Void> addLike(@PathVariable Long clubId) {
        Long userId = UserInfoStore.getUserId();
        clubLikeService.addLike(clubId, userId);
        return ResponseDTO.ok(null);
    }

    @Operation(summary = "동아리 좋아요 취소")
    @DeleteMapping("/{clubId}/like")
    @LoginRequired
    public ResponseDTO<Void> removeLike(@PathVariable Long clubId) {
        Long userId = UserInfoStore.getUserId();
        clubLikeService.removeLike(clubId, userId);
        return ResponseDTO.ok(null);
    }
}