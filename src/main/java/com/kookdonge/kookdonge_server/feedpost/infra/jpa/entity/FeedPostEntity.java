package com.kookdonge.kookdonge_server.feedpost.infra.jpa.entity;

import com.kookdonge.kookdonge_server.common.entity.BaseTimeEntity;
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

    @NotNull
    @Column(name = "feed_id", nullable = false)
    private Long feedId;

    public static FeedPostEntity ofDB(String postUrl, Long feedId) {
        return FeedPostEntity.builder()
                        .postUrl(postUrl)
                        .feedId(feedId)
                .build();
    }
}
