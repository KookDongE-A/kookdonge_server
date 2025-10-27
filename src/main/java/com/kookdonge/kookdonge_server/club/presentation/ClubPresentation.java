package com.kookdonge.kookdonge_server.club.presentation;

import com.kookdonge.kookdonge_server.auth.service.annotation.LoginRequired;
import com.kookdonge.kookdonge_server.club.presentation.dto.req.ClubListReq;
import com.kookdonge.kookdonge_server.club.presentation.dto.res.ClubDetailRes;
import com.kookdonge.kookdonge_server.club.presentation.dto.res.ClubListRes;
import com.kookdonge.kookdonge_server.club.service.ClubLikeService;
import com.kookdonge.kookdonge_server.club.service.ClubService;
import com.kookdonge.kookdonge_server.common.dto.ResponseDTO;
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
import org.springframework.web.bind.annotation.RestController;

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
            @ModelAttribute ClubListReq request,
            @PageableDefault(size = 20, sort = "createdAt", direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ClubListRes> clubList = clubService.getClubList(request, pageable);
        return ResponseDTO.ok(clubList);
    }

    @Operation(summary = "동아리 상세 조회")
    @GetMapping("/{clubId}")
    public ResponseDTO<ClubDetailRes> getClubDetail(@PathVariable Long clubId) {
        ClubDetailRes clubDetail = clubService.getClubDetail(clubId);
        return ResponseDTO.ok(clubDetail);
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