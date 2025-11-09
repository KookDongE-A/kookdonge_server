package com.kookdonge.kookdonge_server.club.infra.jpa.repository;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ClubSortType {

    LATEST("latest"),
    POPULARITY("popularity"),
    DEADLINE("deadline");

    private final String value;

    public static ClubSortType from(String value) {
        for (ClubSortType type : values()) {
            if (type.value.equalsIgnoreCase(value)) {
                return type;
            }
        }
        return LATEST;
    }
}