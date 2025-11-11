package com.kookdonge.kookdonge_server.waitinglist.service

import com.kookdonge.kookdonge_server.club.common.ClubExceptionCode
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubEntity
import com.kookdonge.kookdonge_server.club.infra.jpa.repository.ClubRepository
import com.kookdonge.kookdonge_server.common.exception.CustomException
import com.kookdonge.kookdonge_server.waitinglist.infra.jpa.entity.WaitingListEntity
import com.kookdonge.kookdonge_server.waitinglist.infra.jpa.repository.WaitingListRepository
import com.kookdonge.kookdonge_server.waitinglist.service.dto.ClubInWaitingListDto
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class WaitingListService(
    private val waitingListRepository: WaitingListRepository,
    private val clubRepository: ClubRepository,
) {

    @Transactional
    fun subscribeWaitList(clubId: Long, userId: Long) {

        if(!clubRepository.existsByClubId(clubId)){
            throw CustomException(ClubExceptionCode.CLUB_NOT_FOUND)
        }

        val waitingListEntity: WaitingListEntity = WaitingListEntity(userId, clubId)

        waitingListRepository.save(waitingListEntity)
    }

    @Transactional
    fun unsubscribeWaitList(clubId: Long, userId: Long) {
        waitingListRepository.deleteByClubIdAndUserId(clubId, userId)
    }

    @Transactional(readOnly = true)
    fun getAllWaitingLists(userId: Long): List<ClubInWaitingListDto> {
        val clubsInWaitingListEntity = waitingListRepository.getClubsAllByUserId(userId)

        val clubsInWaitingList: List<ClubInWaitingListDto> = clubsInWaitingListEntity.stream()
            .map { clubEntity ->
                ClubInWaitingListDto.of(
                    clubEntity.clubId,
                    clubEntity.clubName,
                    clubEntity.clubProfileImageUrl,
                    clubEntity.clubType
                )
            }.toList()
        return clubsInWaitingList
    }
}