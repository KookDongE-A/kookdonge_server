package com.kookdonge.kookdonge_server.club.infra.jpa.entity;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@EqualsAndHashCode
public class ClubLikeId implements Serializable {
    private Long clubId;
    private Long userId;

    public static ClubLikeId of(Long clubId, Long userId) {
        return new ClubLikeId(clubId, userId);
    }
}
