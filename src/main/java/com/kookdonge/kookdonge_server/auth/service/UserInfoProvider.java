package com.kookdonge.kookdonge_server.auth.service;

import com.kookdonge.kookdonge_server.auth.common.AuthExceptionCode;
import com.kookdonge.kookdonge_server.auth.infra.jpa.entity.UserEntity;
import com.kookdonge.kookdonge_server.auth.infra.jpa.repository.UserRepository;
import com.kookdonge.kookdonge_server.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserInfoProvider {

    private final UserRepository userRepository;

    // AuthInterceptor에서 사용하기 위해서 만듬
    public Long getUserIdByExternalUserId(String externalUserId){
        UserEntity savedUserEntity = userRepository.findByExternalUserId(externalUserId)
                .orElseThrow(() -> new CustomException(AuthExceptionCode.USER_NOT_FOUND));
        return savedUserEntity.getUserId();
    }
    public Long getClubIdByUserId(Long userId){
        UserEntity savedUserEntity = userRepository.findById(userId)
                .orElseThrow(() -> new CustomException(AuthExceptionCode.USER_NOT_FOUND));
        return savedUserEntity.getClubId();
    }
}
