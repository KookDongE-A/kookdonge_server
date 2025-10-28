package com.kookdonge.kookdonge_server.waitinglist.common

import com.kookdonge.kookdonge_server.common.exception.ExceptionCode

enum class WaitingListExceptionCode(
    private val message: String,
    private val code: Int
) : ExceptionCode {
    NOT_SEND_WAITINGLIST_NOTIFICATION("대기자 명단 알림 전송에 실패했습니다.", 5006)
    ;

    override fun getCode(): Int = code
    override fun getMessage(): String = message
}