package com.kookdonge.kookdonge_server.feed.presentation.dto.req;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class PresignedUrlListReq {
    @NotEmpty(message = "최소 한 개 이상의 이미지가 필요합니다.")
    private List<PresignedUrlReq> presignedUrlList;
}
