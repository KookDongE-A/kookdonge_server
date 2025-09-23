package com.kookdonge.kookdonge_server.feedpost.infra.jpa.repository;

import com.kookdonge.kookdonge_server.feedpost.infra.jpa.entity.FeedPostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedPostRepository extends JpaRepository<FeedPostEntity, Long> {
    List<FeedPostEntity> findAllByFeedId(Long feedId);
}
