package com.kookdonge.kookdonge_server.club.infra.jpa.entity;

import com.kookdonge.kookdonge_server.common.BaseTimeEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;


@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class ClubEntity extends BaseTimeEntity {

    @Id
    private Long clubId;

    @NotBlank
    private String clubName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ClubType clubType;

    @NotBlank
    private String targetGraduate;

    private boolean isLeaveOfAbsenceActive;

    @NotNull
    @Min(0)
    private Long weeklyViewCount;

    @NotBlank
    private String leaderName;

    @Nullable
    private String clubRoomLocation;

    @NotNull
    private Double weeklyActivityFrequency;

    @NotBlank
    private String regularMeeting;

    @NotNull
    private RecruitmentStatus recruitmentStatus;

    @NotNull
    private LocalDateTime recruitmentStartTime;

    @NotNull
    private LocalDateTime recruitmentEndTime;

    @Nullable
    private String clubProfileImageUrl;

    @Lob
    @Nullable
    private String content;

    @NotNull
    @Min(0)
    private Integer count;
}
