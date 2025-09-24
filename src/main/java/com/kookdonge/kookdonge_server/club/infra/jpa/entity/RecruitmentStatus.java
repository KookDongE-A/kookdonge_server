package com.kookdonge.kookdonge_server.club.infra.jpa.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum RecruitmentStatus {

    RECRUITING("모집 중"),
    SCHEDULED("모집 예정"),
    CLOSED("모집 마감")
    ;

    private final String description;
}
