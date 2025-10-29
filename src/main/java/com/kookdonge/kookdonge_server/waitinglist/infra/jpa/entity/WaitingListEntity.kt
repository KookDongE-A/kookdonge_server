package com.kookdonge.kookdonge_server.waitinglist.infra.jpa.entity

import com.kookdonge.kookdonge_server.common.entity.BaseTimeEntity
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import org.jetbrains.annotations.NotNull

@Entity
class WaitingListEntity(

    @NotNull
    val userId: Long,
    @NotNull
    val clubId: Long,
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val waitingListId: Long? = null
) : BaseTimeEntity() {

}