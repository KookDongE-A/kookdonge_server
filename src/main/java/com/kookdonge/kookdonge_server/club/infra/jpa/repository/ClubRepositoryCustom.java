package com.kookdonge.kookdonge_server.club.infra.jpa.repository;

import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubCategory;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubEntity;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubType;
import com.kookdonge.kookdonge_server.club.infra.jpa.entity.RecruitmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ClubRepositoryCustom {

    Page<ClubEntity> findAllClubs(
            ClubCategory category,
            ClubType type,
            RecruitmentStatus recruitmentStatus,
            Integer targetGraduate,
            Integer weeklyActiveFrequency,
            String query,
            Pageable pageable
    );
}