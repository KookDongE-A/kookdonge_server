package com.kookdonge.kookdonge_server.feed.infra.jpa.repository;

import com.kookdonge.kookdonge_server.feed.infra.jpa.entity.FeedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedRepository extends JpaRepository<FeedEntity, Long> {
}
