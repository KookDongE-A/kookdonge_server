package com.kookdonge.kookdonge_server.waitinglist.presentation

import com.kookdonge.kookdonge_server.auth.service.annotation.LoginRequired
import com.kookdonge.kookdonge_server.common.dto.ResponseDTO
import com.kookdonge.kookdonge_server.common.info.UserInfoStore
import com.kookdonge.kookdonge_server.waitinglist.service.WaitingListService
import com.kookdonge.kookdonge_server.waitinglist.service.dto.ClubInWaitingListDto
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "Waiting List", description = "대기자 명단 API")
@RestController
class WaitingListController(
    private val waitingListService: WaitingListService
) {

    @LoginRequired
    @PostMapping("/api/clubs/{clubId}/waiting")
    fun subscribeWaitList(@PathVariable clubId: Long): ResponseDTO<Void> {

        val userId: Long = UserInfoStore.getUserId()
        waitingListService.subscribeWaitList(clubId, userId)

        return ResponseDTO.ok()
    }

    @LoginRequired
    @DeleteMapping("/api/clubs/{clubId}/waiting")
    fun unsubscribeWaitList(@PathVariable clubId: Long): ResponseDTO<Void> {

        val userId: Long = UserInfoStore.getUserId()
        waitingListService.unsubscribeWaitList(clubId, userId)

        return ResponseDTO.ok()
    }

    @LoginRequired
    @GetMapping("/api/waiting-lists")
    fun getWaitingLists(): ResponseDTO<List<ClubInWaitingListDto>>{
        val allWaitingLists = waitingListService.getAllWaitingLists(UserInfoStore.getUserId())
        return ResponseDTO.ok(allWaitingLists)
    }
}