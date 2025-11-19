package com.kookdonge.kookdonge_server.club.service;

import com.kookdonge.kookdonge_server.club.common.ClubExceptionCode;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubCategory;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubType;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.RecruitmentStatus;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubEntity;
import com.kookdonge.kookdonge_server.club.infra.jpa.repository.ClubLikeRepository;
import com.kookdonge.kookdonge_server.club.infra.jpa.repository.ClubRepository;
import com.kookdonge.kookdonge_server.club.presentation.dto.res.ClubDetailRes;
import com.kookdonge.kookdonge_server.club.presentation.dto.res.ClubListRes;
import com.kookdonge.kookdonge_server.club.presentation.dto.res.ClubRankingRes;
import com.kookdonge.kookdonge_server.common.exception.CustomException;
import com.kookdonge.kookdonge_server.common.util.WebUtils;
import jakarta.servlet.http.HttpServletRequest;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import com.kookdonge.kookdonge_server.common.info.UserInfoStore;
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
    private final ClubLikeRepository clubLikeRepository;

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

    @Transactional
    public ClubDetailRes getClubDetail(Long clubId, HttpServletRequest request) {
        Long userId = UserInfoStore.getUserIdOrNull();
        String ipAddress = WebUtils.getClientIp(request);
        String userAgent = WebUtils.getUserAgent(request);

        clubStatsService.incrementViewCount(clubId, userId, ipAddress, userAgent);

        ClubEntity club = clubRepository.findById(clubId)
                .orElseThrow(() -> new CustomException(ClubExceptionCode.CLUB_NOT_FOUND));

        Boolean isLiked = checkIfLiked(clubId, userId);

        return ClubDetailRes.of(club, isLiked);
    }

    public Page<ClubRankingRes> getTopClubsByWeeklyView(Pageable pageable) {
        List<Entry<Long, Long>> topClubs = clubStatsService.getTopClubsByWeeklyView(pageable.getPageSize());
        return buildClubRankingPage(topClubs, pageable);
    }

    public Page<ClubRankingRes> getTopClubsByWeeklyLike(Pageable pageable) {
        LocalDateTime weekStart = getStartOfWeek();
        List<ClubLikeRepository.ClubLikeCount> topClubCounts = clubLikeRepository.findTopClubsByLikesSince(weekStart);

        List<Entry<Long, Long>> topClubs = topClubCounts.stream()
                .limit(pageable.getPageSize())
                .map(count -> new AbstractMap.SimpleEntry<>(count.getClubId(), count.getLikeCount()))
                .collect(Collectors.toList());

        return buildClubRankingPage(topClubs, pageable);
    }

    private Page<ClubRankingRes> buildClubRankingPage(List<Entry<Long, Long>> topClubs, Pageable pageable) {
        Long userId = UserInfoStore.getUserIdOrNull();
        LocalDateTime weekStart = getStartOfWeek();

        List<Long> clubIds = topClubs.stream()
                .map(Entry::getKey)
                .collect(Collectors.toList());

        List<ClubEntity> clubEntities = clubRepository.findAllById(clubIds);

        List<ClubRankingRes> clubs = clubEntities.stream()
                .map(club -> {
                    Long clubId = club.getClubId();
                    Long weeklyViewGrowth = clubStatsService.getWeeklyViewCount(clubId);
                    Long weeklyLikeGrowth = clubLikeRepository.countByClubIdSince(clubId, weekStart);
                    return ClubRankingRes.of(club, checkIfLiked(clubId, userId), weeklyViewGrowth, weeklyLikeGrowth);
                })
                .collect(Collectors.toList());

        return new PageImpl<>(clubs, pageable, clubs.size());
    }

    private LocalDateTime getStartOfWeek() {
        return LocalDateTime.now()
                .with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    private boolean checkIfLiked(Long clubId, Long userId) {
      if (userId == null) {
        return false;
      }
      return clubLikeRepository.existsByClubLikeId_ClubIdAndClubLikeId_UserId(clubId, userId);
    }
}