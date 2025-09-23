package com.kookdonge.kookdonge_server.feed.presentation.dto.req;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;

import java.util.List;

@Getter
public class FeedCreatedReq {
    @NotBlank(message = "피드 내용은 필수입니다.")
    private String content;
    @NotEmpty(message = "최소 한 개 이상의 이미지가 필요합니다.")
    private List<PostUrlReq> postUrls;
}
