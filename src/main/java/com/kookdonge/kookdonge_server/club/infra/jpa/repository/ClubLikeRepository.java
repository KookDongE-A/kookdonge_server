package com.kookdonge.kookdonge_server.club.infra.jpa.repository;

import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubLikeEntity;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ClubLikeRepository extends JpaRepository<ClubLikeEntity, ClubLikeId> {

    boolean existsByClubLikeId_ClubIdAndClubLikeId_UserId(Long clubId, Long userId);

    void deleteByClubLikeId_ClubIdAndClubLikeId_UserId(Long clubId, Long userId);

    @Query("SELECT COUNT(cl) FROM ClubLikeEntity cl WHERE cl.clubLikeId.clubId = :clubId AND cl.createdAt >= :since")
    Long countByClubIdSince(@Param("clubId") Long clubId, @Param("since") LocalDateTime since);

    @Query("SELECT cl.clubLikeId.clubId as clubId, COUNT(cl) as likeCount " +
           "FROM ClubLikeEntity cl " +
           "WHERE cl.createdAt >= :since " +
           "GROUP BY cl.clubLikeId.clubId " +
           "ORDER BY likeCount DESC")
    List<ClubLikeCount> findTopClubsByLikesSince(@Param("since") LocalDateTime since);

    interface ClubLikeCount {
        Long getClubId();
        Long getLikeCount();
    }
}
