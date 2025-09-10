package com.kookdonge.kookdonge_server.auth.service;

import com.kookdonge.kookdonge_server.auth.common.AuthExceptionCode;
import com.kookdonge.kookdonge_server.auth.common.JWT;
import com.kookdonge.kookdonge_server.auth.infra.jpa.entity.UserEntity;
import com.kookdonge.kookdonge_server.auth.infra.jpa.repository.UserRepository;
import com.kookdonge.kookdonge_server.common.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JWT jwt;

    public String registerUser(String email, String phoneNumber, String department, String studentId){

        if (isUserRegistered(email)){
            throw new CustomException(AuthExceptionCode.ALREADY_REGISTERED_USER);
        }

        UserEntity newUserEntity = UserEntity.ofDB(email, phoneNumber, department, studentId);
        UserEntity savedUserEntity = userRepository.save(newUserEntity);
        String externalUserId = savedUserEntity.getExternalUserId();

        return jwt.generateAccessToken(externalUserId);
    }

    public boolean isUserRegistered(String email){
        return userRepository.findByEmail(email).isPresent();
    }

}
