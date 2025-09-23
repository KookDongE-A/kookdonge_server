package com.kookdonge.kookdonge_server.feed.infra.jpa.entity;

import com.kookdonge.kookdonge_server.common.BaseTimeEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class FeedEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @NotNull
    @Column(nullable = false)
    private String content;

    @NotNull
    @Column(name = "club_id", nullable = false)
    private Long clubId;

    public static FeedEntity ofDB(Long clubId, String content) {
        return FeedEntity.builder()
                        .clubId(clubId)
                        .content(content)
                .build();
    }

}
