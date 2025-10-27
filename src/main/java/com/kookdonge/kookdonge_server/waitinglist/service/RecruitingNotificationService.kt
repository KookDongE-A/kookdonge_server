package com.kookdonge.kookdonge_server.waitinglist.service

import com.kookdonge.kookdonge_server.auth.infra.jpa.entity.UserEntity
import com.kookdonge.kookdonge_server.auth.infra.jpa.repository.UserRepository
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubEntity
import com.kookdonge.kookdonge_server.club.infra.jpa.repository.ClubRepository
import com.kookdonge.kookdonge_server.waitinglist.infra.jpa.entity.WaitingListEntity
import com.kookdonge.kookdonge_server.waitinglist.infra.jpa.repository.WaitingListRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class RecruitingNotificationService(
    private val waitingListRepository: WaitingListRepository,
    private val clubRepository: ClubRepository,
    private val userRepository: UserRepository,
) {

    @Transactional()
    fun notifyClubsStartingRecruitmentToday(){

        val clubEntityList: List<ClubEntity> = findClubsStartingRecruitmentToday();

        val userEntityPerClubEntityMap: MutableMap<ClubEntity, List<UserEntity>> = mutableMapOf();

        for (clubEntity in clubEntityList) {
            clubEntity.startRecruitment();

            val clubId = clubEntity.clubId
            val userIdList: List<Long> = waitingListRepository.findAllUserIdByClubId(clubId)

            val findAllByUserIdList : List<UserEntity> = userRepository.findAllByUserIdList(userIdList)

            userEntityPerClubEntityMap.put(clubEntity, findAllByUserIdList)
        }




    }

    fun findClubsStartingRecruitmentToday(): List<ClubEntity> {
        val today: LocalDateTime = LocalDateTime.now();
        val clubEntityList: List<ClubEntity> =
            waitingListRepository.findAllBetweenRecruitingStartDateAndRecruitingEndDate(today);
        return clubEntityList;
    }
}