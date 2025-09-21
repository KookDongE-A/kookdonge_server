package com.kookdonge.kookdonge_server.auth.presentation.dto.req;

import lombok.Getter;

@Getter
public class ReissueAccessTokenReq {
    private String refreshToken;
}
