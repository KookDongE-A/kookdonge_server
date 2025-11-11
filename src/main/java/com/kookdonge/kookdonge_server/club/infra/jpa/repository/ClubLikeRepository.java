package com.kookdonge.kookdonge_server.club.infra.jpa.repository;

import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubLikeEntity;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClubLikeRepository extends JpaRepository<ClubLikeEntity, ClubLikeId> {

    boolean existsByClubLikeId_ClubIdAndClubLikeId_UserId(Long clubId, Long userId);

    void deleteByClubLikeId_ClubIdAndClubLikeId_UserId(Long clubId, Long userId);

    @Query("SELECT cl FROM ClubLikeEntity cl WHERE cl.clubLikeId.userId = :userId ORDER BY cl.createdAt DESC")
    List<ClubLikeEntity> findAllByUserId(@Param("userId") Long userId);
}
