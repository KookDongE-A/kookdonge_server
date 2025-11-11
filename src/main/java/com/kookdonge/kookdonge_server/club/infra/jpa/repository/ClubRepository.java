package com.kookdonge.kookdonge_server.club.infra.jpa.repository;

import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClubRepository extends JpaRepository<ClubEntity, Long>, ClubRepositoryCustom {

    @Modifying
    @Query("UPDATE ClubEntity c SET c.totalViewCount = c.totalViewCount + :count WHERE c.clubId = :clubId")
    void incrementTotalViewCount(@Param("clubId") Long clubId, @Param("count") Long count);

    @Modifying
    @Query("UPDATE ClubEntity c SET c.totalLikeCount = c.totalLikeCount + :count WHERE c.clubId = :clubId")
    void incrementTotalLikeCount(@Param("clubId") Long clubId, @Param("count") Long count);

    boolean existsByClubId(Long clubId);
}
