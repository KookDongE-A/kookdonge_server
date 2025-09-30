package com.kookdonge.kookdonge_server.club.service;

import com.kookdonge.kookdonge_server.club.common.ClubExceptionCode;
import com.kookdonge.kookdonge_server.club.infra.jpa.repository.ClubRepository;
import com.kookdonge.kookdonge_server.club.presentation.dto.req.ClubListReq;
import com.kookdonge.kookdonge_server.club.presentation.dto.res.ClubDetailRes;
import com.kookdonge.kookdonge_server.club.presentation.dto.res.ClubListRes;
import com.kookdonge.kookdonge_server.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubService {

    private final ClubRepository clubRepository;

    public Page<ClubListRes> getClubList(ClubListReq request, Pageable pageable) {
        return clubRepository.findAll(pageable)
                .map(club -> ClubListRes.of(club));
    }

    public ClubDetailRes getClubDetail(Long clubId) {
        return clubRepository.findById(clubId)
                .map(ClubDetailRes::of)
                .orElseThrow(() -> new CustomException(ClubExceptionCode.CLUB_NOT_FOUND));
    }
}