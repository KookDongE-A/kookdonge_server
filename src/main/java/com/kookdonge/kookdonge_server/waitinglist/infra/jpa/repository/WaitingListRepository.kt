package com.kookdonge.kookdonge_server.waitinglist.infra.jpa.repository

import com.kookdonge.kookdonge_server.waitinglist.infra.dto.UserAndClubDTO
import com.kookdonge.kookdonge_server.waitinglist.infra.jpa.entity.WaitingListEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import java.time.LocalDateTime

interface WaitingListRepository : JpaRepository<WaitingListEntity, Long> {

    @Query("select tmp.clubName as clubName, ue.email as userEmail " +
            "from UserEntity ue " +
            "inner join " +
            "(select ce.clubName as clubName, wle.userId as userId " +
            "from ClubEntity ce " +
            "inner join WaitingListEntity wle " +
            "on ce.clubId = wle.clubId " +
            "where ce.recruitmentStatus = 'SCHEDULED' " +
            "and ce.recruitmentStartTime <= :today " +
            "and ce.recruitmentEndTime >= :today) tmp " +
            "on ue.userId = tmp.userId"
            )
    fun findAllBetweenRecruitingStartDateAndRecruitingEndDate(today: LocalDateTime): List<UserAndClubDTO>

    @Query("select wle " +
            "from WaitingListEntity wle " +
            "where wle.clubId in :clubIdList")
    fun findAllByClubIdList(clubIdList: List<Long>): List<WaitingListEntity>

    @Query("select wle.userId " +
            "from WaitingListEntity wle " +
            "where wle.clubId = :clubId")
    fun findAllUserIdByClubId(clubId: Long): List<Long>

}