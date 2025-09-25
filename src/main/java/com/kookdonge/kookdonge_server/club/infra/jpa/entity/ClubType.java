package com.kookdonge.kookdonge_server.club.infra.jpa.entity;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ClubType {

    CENTRAL("중앙동아리"),
    DEPARTMENTAL("과동아리")
    ;

    private final String description;
}
