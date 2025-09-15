package com.kookdonge.kookdonge_server.feedpost.infra.jpa.entity;

import com.kookdonge.kookdonge_server.common.BaseTimeEntity;
import com.kookdonge.kookdonge_server.feed.infra.jpa.entity.FeedEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class FeedPostEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedPostId;

    @NotNull
    @Column(nullable = false)
    private String postUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id", nullable = false)
    private FeedEntity feedEntity;

    public static FeedPostEntity of(String postUrl, FeedEntity feedEntity) {
        return FeedPostEntity.builder()
                        .postUrl(postUrl)
                        .feedEntity(feedEntity)
                .build();
    }
}
