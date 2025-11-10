package com.kookdonge.kookdonge_server.auth.infra.jpa.repository;

import com.kookdonge.kookdonge_server.auth.infra.jpa.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByExternalUserId(String externalUserId);

    @Query("select ue " +
            "from UserEntity ue " +
            "where ue.userId in :userIdList")
    List<UserEntity> findAllByUserIdList(List<Long> userIdList);
}
