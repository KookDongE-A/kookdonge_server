package com.kookdonge.kookdonge_server.club.presentation.dto.res;

import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubCategory;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubEntity;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubType;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.RecruitmentStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClubListRes {

    private Long id;
    private String name;
    private String logoImage;
    private String introduction;
    private ClubType type;
    private ClubCategory category;
    private RecruitmentStatus recruitmentStatus;
    private Long dDay;
    private Boolean isLikedByMe;

    public static ClubListRes of(ClubEntity club) {
        Long dDay = null;
        if (club.getRecruitmentStatus() == RecruitmentStatus.RECRUITING) {
            dDay = ChronoUnit.DAYS.between(LocalDateTime.now(), club.getRecruitmentEndTime());
        }

        return new ClubListRes(
                club.getClubId(),
                club.getClubName(),
                club.getClubProfileImageUrl(),
                club.getContent(),
                club.getClubType(),
                club.getCategory(),
                club.getRecruitmentStatus(),
                dDay,
                false
        );
    }
}