package com.kookdonge.kookdonge_server.waitinglist.schedular

import com.kookdonge.kookdonge_server.auth.presentation.UserPresentation
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubEntity
import com.kookdonge.kookdonge_server.waitinglist.infra.dto.UserAndClubDTO
import com.kookdonge.kookdonge_server.waitinglist.infra.jpa.entity.WaitingListEntity
import com.kookdonge.kookdonge_server.waitinglist.infra.jpa.repository.WaitingListRepository
import com.kookdonge.kookdonge_server.waitinglist.infra.mail.GmailSender
import com.kookdonge.kookdonge_server.waitinglist.service.RecruitingNotificationService
import jakarta.transaction.Transactional
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

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