package com.kookdonge.kookdonge_server.club.service;

import com.kookdonge.kookdonge_server.club.common.ClubExceptionCode;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubEntity;
import com.kookdonge.kookdonge_server.club.infra.jpa.repository.ClubLikeRepository;
import com.kookdonge.kookdonge_server.club.infra.jpa.repository.ClubRepository;
import com.kookdonge.kookdonge_server.club.presentation.dto.req.ClubListReq;
import com.kookdonge.kookdonge_server.club.presentation.dto.res.ClubDetailRes;
import com.kookdonge.kookdonge_server.club.presentation.dto.res.ClubListRes;
import com.kookdonge.kookdonge_server.common.exception.CustomException;
import com.kookdonge.kookdonge_server.common.info.UserInfoStore;
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
    private final ClubLikeRepository clubLikeRepository;

    public Page<ClubListRes> getClubList(ClubListReq request, Pageable pageable) {
        Long userId = UserInfoStore.getUserId();

        return clubRepository.findAll(pageable)
                .map(club -> {
                    boolean isLikedByMe = checkIfLiked(club.getClubId(), userId);
                    return ClubListRes.of(club, isLikedByMe);
                });
    }

    public ClubDetailRes getClubDetail(Long clubId) {
        ClubEntity club = clubRepository.findById(clubId)
                .orElseThrow(() -> new CustomException(ClubExceptionCode.CLUB_NOT_FOUND));

        Long userId = UserInfoStore.getUserId();
        boolean isLikedByMe = checkIfLiked(clubId, userId);

        return ClubDetailRes.of(club, isLikedByMe);
    }

    private boolean checkIfLiked(Long clubId, Long userId) {
        if (userId == null) {
            return false;
        }
        return clubLikeRepository.existsByClubLikeId_ClubIdAndClubLikeId_UserId(clubId, userId);
    }
}