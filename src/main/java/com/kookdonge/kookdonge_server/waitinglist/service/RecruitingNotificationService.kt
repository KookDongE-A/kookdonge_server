package com.kookdonge.kookdonge_server.waitinglist.service

import com.kookdonge.kookdonge_server.auth.infra.jpa.entity.UserEntity
import com.kookdonge.kookdonge_server.auth.infra.jpa.repository.UserRepository
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubEntity
import com.kookdonge.kookdonge_server.club.infra.jpa.repository.ClubRepository
import com.kookdonge.kookdonge_server.waitinglist.infra.dto.UserAndClubDTO
import com.kookdonge.kookdonge_server.waitinglist.infra.jpa.entity.WaitingListEntity
import com.kookdonge.kookdonge_server.waitinglist.infra.jpa.repository.WaitingListRepository
import com.kookdonge.kookdonge_server.waitinglist.infra.mail.GmailSender
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class RecruitingNotificationService(
    private val waitingListRepository: WaitingListRepository,
    private val clubRepository: ClubRepository,
    private val userRepository: UserRepository,
    private val gmailSender: GmailSender
) {

    @Transactional()
    fun notifyClubsStartingRecruitmentToday(){

        val userAndClubDTOList: List<UserAndClubDTO> = findClubsStartingRecruitmentToday();
        for (userAndClub in userAndClubDTOList) {
            val clubName = userAndClub.clubName
            val userEmail = userAndClub.userEmail

            gmailSender.sendNotificationEmailToStartingRecruiting(userEmail, clubName)
        }




    }

    fun findClubsStartingRecruitmentToday(): List<UserAndClubDTO> {
        val today: LocalDateTime = LocalDateTime.now();
        val userAndClubDTOList: List<UserAndClubDTO> =
            waitingListRepository.findAllBetweenRecruitingStartDateAndRecruitingEndDate(today);
        return userAndClubDTOList;
    }
}