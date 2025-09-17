package com.kookdonge.kookdonge_server.auth.presentation.dto.req;

import lombok.Getter;

@Getter
public class RegisterUserReq {
    public String googleGrantCode;
    public String department;
    public String studentId;
    public String phoneNumber;
}
