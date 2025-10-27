package com.kookdonge.kookdonge_server.club.infra.jpa.repository;

import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubLikeEntity;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubLikeId;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubLikeRepository extends JpaRepository<ClubLikeEntity, ClubLikeId> {

    boolean existsByClubLikeId_ClubIdAndClubLikeId_UserId(Long clubId, Long userId);

    void deleteByClubLikeId_ClubIdAndClubLikeId_UserId(Long clubId, Long userId);
}
