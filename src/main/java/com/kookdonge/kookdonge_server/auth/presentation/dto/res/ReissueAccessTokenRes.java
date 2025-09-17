package com.kookdonge.kookdonge_server.auth.presentation.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class ReissueAccessTokenRes {
    private String accessToken;
}
