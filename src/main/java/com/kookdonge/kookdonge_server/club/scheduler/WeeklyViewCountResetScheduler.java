package com.kookdonge.kookdonge_server.club.scheduler;

import com.kookdonge.kookdonge_server.club.service.ClubStatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class WeeklyViewCountResetScheduler {

    private final ClubStatsService clubStatsService;

    @Scheduled(cron = "0 0 0 * * MON")
    public void resetWeeklyStats() {
        log.info("주간 조회수 집계 초기화 시작");
        try {
            clubStatsService.resetWeeklyStats();
            log.info("주간 조회수 집계 초기화 완료");
        } catch (Exception e) {
            log.error("주간 조회수 집계 초기화 실패", e);
        }
    }
}