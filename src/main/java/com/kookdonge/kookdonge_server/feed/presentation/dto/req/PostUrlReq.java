package com.kookdonge.kookdonge_server.feed.presentation.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PostUrlReq {
    @NotBlank(message = "Url은 필수입니다.")
    private String postUrl;
}
