package com.kookdonge.kookdonge_server.waitinglist.schedular

import com.kookdonge.kookdonge_server.waitinglist.service.RecruitingNotificationService
import jakarta.transaction.Transactional
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class ClubRecruitingScheduler(
    private val recruitingNotificationService: RecruitingNotificationService
) {

    @Transactional
    @Scheduled(cron = "0 0 * * * *", zone = "Asia/Seoul")
    fun checkClubRecruitingStatus() {
        recruitingNotificationService.notifyClubsStartingRecruitmentToday()
    }
}