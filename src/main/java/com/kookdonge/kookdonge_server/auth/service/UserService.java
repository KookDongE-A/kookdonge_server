package com.kookdonge.kookdonge_server.auth.service;

import com.kookdonge.kookdonge_server.auth.common.AuthExceptionCode;
import com.kookdonge.kookdonge_server.auth.common.JWT;
import com.kookdonge.kookdonge_server.auth.infra.jpa.entity.UserEntity;
import com.kookdonge.kookdonge_server.auth.infra.jpa.repository.UserRepository;
import com.kookdonge.kookdonge_server.auth.service.client.GoogleClient;
import com.kookdonge.kookdonge_server.auth.service.client.dto.req.IssueAccessTokenByGrantCodeReq;
import com.kookdonge.kookdonge_server.auth.service.client.dto.res.GetUserInfoRes;
import com.kookdonge.kookdonge_server.auth.service.client.dto.res.IssueAccessTokenByGrantCodeRes;
import com.kookdonge.kookdonge_server.common.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JWT jwt;

    private GoogleClient googleClient;


    @Value("${google.client.id}")
    private String googleClientId;
    @Value("${google.client.secret}")
    private String googleClientSecret;
    @Value("${google.client.redirect-uri}")
    private String googleClientRedirectUri;

    public String registerUser(String googleGrantCode, String phoneNumber, String department, String studentId){

        IssueAccessTokenByGrantCodeRes issueAccessTokenByGrantCodeRes = googleClient.issueAccessTokenByGrantCode(IssueAccessTokenByGrantCodeReq.fromGrantCode(googleGrantCode, googleClientId, googleClientSecret, googleClientRedirectUri));

        String accessToken = issueAccessTokenByGrantCodeRes.getAccessToken();
        GetUserInfoRes userInfo = googleClient.getUserInfo(accessToken);

        String email = userInfo.getEmail();
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
