package com.kookdonge.kookdonge_server.club.service;

import com.kookdonge.kookdonge_server.club.common.ClubExceptionCode;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubCategory;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubType;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.RecruitmentStatus;
import com.kookdonge.kookdonge_server.club.infra.jpa.repository.ClubRepository;
import com.kookdonge.kookdonge_server.club.presentation.dto.res.ClubDetailRes;
import com.kookdonge.kookdonge_server.club.presentation.dto.res.ClubListRes;
import com.kookdonge.kookdonge_server.common.exception.CustomException;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubService {

    private final ClubRepository clubRepository;
    private final ClubStatsService clubStatsService;

    public Page<ClubListRes> getClubList(
            ClubCategory category,
            ClubType type,
            RecruitmentStatus recruitmentStatus,
            Integer targetGraduate,
            Integer weeklyActiveFrequency,
            String query,
            Pageable pageable
    ) {

        return clubRepository.findAllClubs(
                category,
                type,
                recruitmentStatus,
                targetGraduate,
                weeklyActiveFrequency,
                query,
                pageable
        ).map(ClubListRes::of);
    }

    public ClubDetailRes getClubDetail(Long clubId, Long userId) {
        clubStatsService.incrementViewCount(clubId, userId);

        return clubRepository.findById(clubId)
                .map(ClubDetailRes::of)
                .orElseThrow(() -> new CustomException(ClubExceptionCode.CLUB_NOT_FOUND));
    }

    public boolean toggleClubLike(Long clubId, Long userId) {
        clubRepository.findById(clubId)
                .orElseThrow(() -> new CustomException(ClubExceptionCode.CLUB_NOT_FOUND));

        return clubStatsService.toggleLike(clubId, userId);
    }

    public Page<ClubListRes> getTopClubsByWeeklyView(Pageable pageable) {
        List<Entry<Long, Long>> topClubs = clubStatsService.getTopClubsByWeeklyView(pageable.getPageSize());
        return buildClubListPage(topClubs, pageable);
    }

    public Page<ClubListRes> getTopClubsByWeeklyLike(Pageable pageable) {
        List<Entry<Long, Long>> topClubs = clubStatsService.getTopClubsByWeeklyLike(pageable.getPageSize());
        return buildClubListPage(topClubs, pageable);
    }

    private Page<ClubListRes> buildClubListPage(List<Entry<Long, Long>> topClubs, Pageable pageable) {
        List<Long> clubIds = topClubs.stream()
                .map(Entry::getKey)
                .collect(Collectors.toList());

        List<ClubListRes> clubs = clubRepository.findAllById(clubIds).stream()
                .map(ClubListRes::of)
                .collect(Collectors.toList());

        return new PageImpl<>(clubs, pageable, clubs.size());
    }
}