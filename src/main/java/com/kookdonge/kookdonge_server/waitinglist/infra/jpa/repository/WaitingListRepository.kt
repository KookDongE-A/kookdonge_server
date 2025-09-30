package com.kookdonge.kookdonge_server.waitinglist.infra.jpa.repository

import com.kookdonge.kookdonge_server.waitinglist.infra.jpa.entity.WaitingListEntity
import org.springframework.data.jpa.repository.JpaRepository

interface WaitingListRepository : JpaRepository<WaitingListEntity, Long> {
}