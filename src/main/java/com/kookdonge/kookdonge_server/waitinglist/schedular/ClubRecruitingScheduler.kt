package com.kookdonge.kookdonge_server.waitinglist.schedular

import com.kookdonge.kookdonge_server.auth.presentation.UserPresentation
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubEntity
import com.kookdonge.kookdonge_server.waitinglist.infra.dto.UserAndClubDTO
import com.kookdonge.kookdonge_server.waitinglist.infra.jpa.entity.WaitingListEntity
import com.kookdonge.kookdonge_server.waitinglist.infra.jpa.repository.WaitingListRepository
import com.kookdonge.kookdonge_server.waitinglist.infra.mail.GmailSender
import jakarta.transaction.Transactional
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ClubRecruitingScheduler(
    private val waitingListRepository: WaitingListRepository,
    private val gmailSender: GmailSender
) {

    @Transactional
    @Scheduled(cron = "0 0 * * * *", zone = "Asia/Seoul")
    fun checkClubRecruitingStatus() {
        val today: LocalDateTime = LocalDateTime.now()
        val userAndClubDTOList : List<UserAndClubDTO> =
            waitingListRepository.findAllBetweenRecruitingStartDateAndRecruitingEndDate(today)

        for (userAndClub in userAndClubDTOList) {
            val clubName = userAndClub.clubName
            val userEmail = userAndClub.userEmail

            gmailSender.sendNotificationEmailToStartingRecruiting(userEmail, clubName)
        }
    }
}