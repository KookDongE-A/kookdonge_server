package com.kookdonge.kookdonge_server.waitinglist.service

import com.kookdonge.kookdonge_server.club.common.ClubExceptionCode
import com.kookdonge.kookdonge_server.club.infra.jpa.repository.ClubRepository
import com.kookdonge.kookdonge_server.common.exception.CustomException
import com.kookdonge.kookdonge_server.waitinglist.infra.jpa.entity.WaitingListEntity
import com.kookdonge.kookdonge_server.waitinglist.infra.jpa.repository.WaitingListRepository
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

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
}