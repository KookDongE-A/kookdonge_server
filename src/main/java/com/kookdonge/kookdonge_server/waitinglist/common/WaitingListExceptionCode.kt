package com.kookdonge.kookdonge_server.waitinglist.common

import com.kookdonge.kookdonge_server.common.exception.ExceptionCode

enum class WaitingListExceptionCode : ExceptionCode {
    NOT_SEND_WAITINGLIST_NOTIFICATION("대기자 명단 알림 전송에 실패했습니다.", 5006)
    ;

    val message: String
    val code: Int

    constructor(message: String, code: Int) {
        this.message = message
        this.code = code
    }

    override fun getCode(): Int {
        return code
    }
    override fun getMessage(): String {
        return message
    }
}