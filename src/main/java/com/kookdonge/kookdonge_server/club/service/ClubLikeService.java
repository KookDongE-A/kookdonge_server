package com.kookdonge.kookdonge_server.club.service;

import com.kookdonge.kookdonge_server.club.common.ClubExceptionCode;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubEntity;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubLikeEntity;
import com.kookdonge.kookdonge_server.club.infra.jpa.repository.ClubLikeRepository;
import com.kookdonge.kookdonge_server.club.infra.jpa.repository.ClubRepository;
import com.kookdonge.kookdonge_server.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClubLikeService {

    private final ClubRepository clubRepository;
    private final ClubLikeRepository clubLikeRepository;

    @Transactional
    public void addLike(Long clubId, Long userId) {
      ClubEntity club = clubRepository.findById(clubId)
                .orElseThrow(() -> new CustomException(ClubExceptionCode.CLUB_NOT_FOUND));

        boolean isLiked = clubLikeRepository.existsByClubLikeId_ClubIdAndClubLikeId_UserId(clubId, userId);
        if (isLiked) {
            return;
        }
        club.increaseLikeCount();
        clubLikeRepository.save(ClubLikeEntity.of(clubId, userId));
    }

    @Transactional
    public void removeLike(Long clubId, Long userId) {
      ClubEntity club = clubRepository.findById(clubId)
                .orElseThrow(() -> new CustomException(ClubExceptionCode.CLUB_NOT_FOUND));

        boolean isLiked = clubLikeRepository.existsByClubLikeId_ClubIdAndClubLikeId_UserId(clubId, userId);
        if (!isLiked) {
            return;
        }
        club.decreaseLikeCount();
        clubLikeRepository.deleteByClubLikeId_ClubIdAndClubLikeId_UserId(clubId, userId);
    }
}