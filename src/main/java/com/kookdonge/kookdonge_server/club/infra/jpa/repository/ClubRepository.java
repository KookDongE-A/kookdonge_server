package com.kookdonge.kookdonge_server.club.infra.jpa.repository;

import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClubRepository extends JpaRepository<ClubEntity, Long> {

    @Modifying
    @Query("UPDATE ClubEntity c SET c.likeCount = c.likeCount + :delta WHERE c.clubId = :clubId")
    void updateLikeCount(@Param("clubId") Long clubId, @Param("delta") int delta);

    boolean existsByClubId(Long clubId);
}
