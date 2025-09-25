package com.kookdonge.kookdonge_server.feed.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(staticName = "of")
public class ClubFeedDto {
    private Long feedId;
    private String content;
    private List<String> postUrls;
}
