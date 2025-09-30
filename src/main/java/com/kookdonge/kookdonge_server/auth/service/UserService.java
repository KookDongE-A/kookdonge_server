package com.kookdonge.kookdonge_server.auth.service;

import com.kookdonge.kookdonge_server.auth.common.AuthExceptionCode;
import com.kookdonge.kookdonge_server.auth.common.JWT;
import com.kookdonge.kookdonge_server.auth.infra.jpa.entity.UserEntity;
import com.kookdonge.kookdonge_server.auth.infra.jpa.repository.UserRepository;
import com.kookdonge.kookdonge_server.auth.service.client.GoogleClient;
import com.kookdonge.kookdonge_server.auth.service.client.GoogleOAuthClient;
import com.kookdonge.kookdonge_server.auth.service.client.dto.req.IssueAccessTokenByGrantCodeReq;
import com.kookdonge.kookdonge_server.auth.service.client.dto.res.GetUserInfoRes;
import com.kookdonge.kookdonge_server.auth.service.client.dto.res.IssueAccessTokenByGrantCodeRes;
import com.kookdonge.kookdonge_server.auth.service.dto.LoginDTO;
import com.kookdonge.kookdonge_server.auth.service.dto.RegisterUserDTO;
import com.kookdonge.kookdonge_server.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final JWT jwt;

    private final GoogleOAuthClient googleOAuthClient;
    private final GoogleClient googleClient;


    @Value("${google.client.id}")
    private String googleClientId;
    @Value("${google.client.secret}")
    private String googleClientSecret;
    @Value("${google.client.redirect-uri}")
    private String googleClientRedirectUri;

    public RegisterUserDTO registerUser(String googleGrantCode, String phoneNumber, String department, String studentId){

        IssueAccessTokenByGrantCodeRes issueAccessTokenByGrantCodeRes = googleOAuthClient.issueAccessTokenByGrantCode(IssueAccessTokenByGrantCodeReq.fromGrantCode(googleGrantCode, googleClientId, googleClientSecret, googleClientRedirectUri));
        log.debug("issueAccessTokenByGrantCodeRes: {}", issueAccessTokenByGrantCodeRes.getScope());

        String googleAccessToken = "Bearer " + issueAccessTokenByGrantCodeRes.getAccessToken();
        GetUserInfoRes userInfo = googleClient.getUserInfo(googleAccessToken);

        String email = userInfo.getEmail();
        if (isUserRegistered(email)){
            throw new CustomException(AuthExceptionCode.ALREADY_REGISTERED_USER);
        }

        UserEntity newUserEntity = UserEntity.ofDB(email, phoneNumber, department, studentId);
        UserEntity savedUserEntity = userRepository.save(newUserEntity);
        String externalUserId = savedUserEntity.getExternalUserId();

        String accessToken = jwt.generateAccessToken(externalUserId);
        String refreshToken = jwt.generateRefreshToken(externalUserId);

        return RegisterUserDTO.of(savedUserEntity.getExternalUserId(),
                savedUserEntity.getEmail(),
                savedUserEntity.getStudentId(),
                savedUserEntity.getPhoneNumber(),
                savedUserEntity.getDepartment(),
                accessToken,
                refreshToken);
    }

    public boolean isUserRegistered(String email){
        return userRepository.findByEmail(email).isPresent();
    }

    public String reissueAccessTokenByRefreshToken(String refreshToken){
        if(!jwt.isTokenValid(refreshToken)){
            throw new CustomException(AuthExceptionCode.INVALID_REFRESH_TOKEN);
        }

        String externalUserId = jwt.extractUserId(refreshToken);
        return jwt.generateAccessToken(externalUserId);
    }

    public LoginDTO loginUser(String googleGrantCode){
        IssueAccessTokenByGrantCodeRes issueAccessTokenByGrantCodeRes = googleOAuthClient.issueAccessTokenByGrantCode(IssueAccessTokenByGrantCodeReq.fromGrantCode(googleGrantCode, googleClientId, googleClientSecret, googleClientRedirectUri));

        String googleAccessToken = "Bearer " + issueAccessTokenByGrantCodeRes.getAccessToken();
        GetUserInfoRes userInfo = googleClient.getUserInfo(googleAccessToken);

        String email = userInfo.getEmail();
        UserEntity savedUserEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(AuthExceptionCode.USER_NOT_FOUND));

        String externalUserId = savedUserEntity.getExternalUserId();
        String accessToken = jwt.generateAccessToken(externalUserId);
        String refreshToken = jwt.generateRefreshToken(externalUserId);

        return LoginDTO.of(savedUserEntity.getExternalUserId(),
                savedUserEntity.getEmail(),
                savedUserEntity.getStudentId(),
                savedUserEntity.getPhoneNumber(),
                savedUserEntity.getDepartment(),
                accessToken,
                refreshToken);
    }

}
