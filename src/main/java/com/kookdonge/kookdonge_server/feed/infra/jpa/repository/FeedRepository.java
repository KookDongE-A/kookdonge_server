package com.kookdonge.kookdonge_server.feed.infra.jpa.repository;

import com.kookdonge.kookdonge_server.feed.infra.jpa.entity.FeedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<FeedEntity, Long> {
    List<FeedEntity> findAllByClubIdOrderByCreatedAtDesc(Long clubId);
    Optional<FeedEntity> findByFeedId(Long feedId);
}
