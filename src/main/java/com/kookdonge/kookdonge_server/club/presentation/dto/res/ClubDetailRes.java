package com.kookdonge.kookdonge_server.club.presentation.dto.res;

import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubEntity;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubType;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.RecruitmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClubDetailRes {

    private Long id;
    private String name;
    private String image;
    private ClubType type;
    private String targetGraduate;
    private String leaderName;
    private String location;
    private Double weeklyActiveFrequency;
    private RecruitmentStatus recruitmentStatus;
    private LocalDateTime recruitmentStartDate;
    private LocalDateTime recruitmentEndDate;
    private Integer like;
    private Integer weeklyView;
    private Boolean isLikedByMe;

    public static ClubDetailRes of(ClubEntity club, Boolean isLikedByMe) {
        return new ClubDetailRes(
                club.getClubId(),
                club.getClubName(),
                club.getClubProfileImageUrl(),
                club.getClubType(),
                club.getTargetGraduate(),
                club.getLeaderName(),
                club.getClubRoomLocation(),
                club.getWeeklyActivityFrequency(),
                club.getRecruitmentStatus(),
                club.getRecruitmentStartTime(),
                club.getRecruitmentEndTime(),
                club.getLikeCount(),
                club.getWeeklyViewCount().intValue(),
                isLikedByMe
        );
    }
}