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
        log.info("주간 조회수/좋아요 집계 초기화 시작");
        try {
            clubStatsService.saveWeeklyStatsToDatabase();
            log.info("주간 통계를 데이터베이스에 저장 완료");

            clubStatsService.resetWeeklyStats();
            log.info("주간 조회수/좋아요 집계 초기화 완료");
        } catch (Exception e) {
            log.error("주간 조회수/좋아요 집계 초기화 실패", e);
        }
    }
}