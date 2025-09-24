package com.kookdonge.kookdonge_server.club.presentation;

import com.kookdonge.kookdonge_server.club.presentation.dto.req.ClubListReq;
import com.kookdonge.kookdonge_server.club.presentation.dto.res.ClubDetailRes;
import com.kookdonge.kookdonge_server.club.presentation.dto.res.ClubListRes;
import com.kookdonge.kookdonge_server.club.service.ClubService;
import com.kookdonge.kookdonge_server.common.dto.ResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/clubs")
@RequiredArgsConstructor
public class ClubPresentation {

    private final ClubService clubService;

    @GetMapping
    public ResponseDTO<Page<ClubListRes>> getClubList(
            @ModelAttribute ClubListReq request,
            @PageableDefault(size = 20, sort = "createdAt", direction = org.springframework.data.domain.Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ClubListRes> clubList = clubService.getClubList(request, pageable);
        return ResponseDTO.ok(clubList);
    }

    @GetMapping("/{clubId}")
    public ResponseDTO<ClubDetailRes> getClubDetail(@PathVariable Long clubId) {
        ClubDetailRes clubDetail = clubService.getClubDetail(clubId);
        return ResponseDTO.ok(clubDetail);
    }
}