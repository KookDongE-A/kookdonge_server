package com.kookdonge.kookdonge_server.club.infra.jpa.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ClubCategory {

    PERFORMING_ARTS("공연예술"),
    LIBERAL_ARTS_SERVICE("교양봉사"),
    EXHIBITION_ARTS("전시예술"),
    RELIGION("종교"),
    BALL_LEISURE("구기레져"),
    PHYSICAL_MARTIAL_ARTS("체육무도"),
    ACADEMIC("학술")
    ;

    private final String description;
}
