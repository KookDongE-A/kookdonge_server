package com.kookdonge.kookdonge_server.club.infra.jpa.entity;

import com.kookdonge.kookdonge_server.common.entity.BaseTimeEntity;
import jakarta.annotation.Nullable;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
import java.util.Objects;


@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder(access = AccessLevel.PRIVATE)
@Getter
@Entity
public class ClubEntity extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long clubId;

    @NotBlank
    @Column(nullable = false)
    private String clubName;

    @NotNull
    @Enumerated(EnumType.STRING)
    private ClubType clubType;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClubCategory category;

    @NotBlank
    @Column(nullable = false)
    private String targetGraduate;

    @NotNull
    @Column(nullable = false)
    private Boolean isLeaveOfAbsenceActive;

    @NotNull
    @Column(nullable = false)
    @Min(0)
    private Long totalViewCount;

    @NotBlank
    @Column(nullable = false)
    private String leaderName;

    @Nullable
    private String clubRoomLocation;

    @NotNull
    @Column(nullable = false)
    private Double weeklyActivityFrequency;

    @NotBlank
    @Column(nullable = false)
    private String regularMeeting;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecruitmentStatus recruitmentStatus;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime recruitmentStartTime;

    @NotNull
    @Column(nullable = false)
    private LocalDateTime recruitmentEndTime;

    @Nullable
    private String clubProfileImageUrl;

    @Nullable
    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    @Column(nullable = false)
    @Min(0)
    private Long totalLikeCount;

    public void startRecruitment() {
        this.recruitmentStatus = RecruitmentStatus.RECRUITING;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        ClubEntity that = (ClubEntity) o;
        return Objects.equals(clubId, that.clubId);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(clubId);
    }

}
