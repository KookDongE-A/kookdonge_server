package com.kookdonge.kookdonge_server.feed.presentation.dto.req;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class PresignedUrlReq {
    @NotBlank(message = "파일 이름은 필수입니다.")
    public String fileName;
}
