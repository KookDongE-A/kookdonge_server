package com.kookdonge.kookdonge_server.club.presentation.dto.res;

import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubEntity;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubType;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.RecruitmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClubListRes {

    private Long id;
    private String name;
    private String image;
    private ClubType type;
    private Integer like;
    private Integer weeklyView;
    private RecruitmentStatus recruitmentStatus;
    private Boolean isLikedByMe;

    public static ClubListRes of(ClubEntity club, Boolean isLikedByMe) {
        return new ClubListRes(
                club.getClubId(),
                club.getClubName(),
                club.getClubProfileImageUrl(),
                club.getClubType(),
                club.getLikeCount(),
                club.getWeeklyViewCount().intValue(),
                club.getRecruitmentStatus(),
                isLikedByMe
        );
    }
}