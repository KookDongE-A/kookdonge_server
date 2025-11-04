package com.kookdonge.kookdonge_server.club.infra.jpa.entity;

import com.kookdonge.kookdonge_server.common.entity.BaseTimeEntity;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class ClubLikeEntity extends BaseTimeEntity {

    @EmbeddedId
    private ClubLikeId clubLikeId;

    public static ClubLikeEntity of(Long clubId, Long userId) {
        return ClubLikeEntity.builder()
                .clubLikeId(ClubLikeId.of(clubId, userId))
                .build();
    }
}
