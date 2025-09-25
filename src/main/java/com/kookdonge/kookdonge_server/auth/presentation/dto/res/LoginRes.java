package com.kookdonge.kookdonge_server.auth.presentation.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class LoginRes {
    private String externalUserId;
    private String email;
    private String studentId;
    private String phoneNumber;
    private String department;
    private String accessToken;
    private String refreshToken;
}
