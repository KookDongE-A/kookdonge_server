package com.kookdonge.kookdonge_server.feed.presentation.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(staticName = "of")
public class ClubFeedRes {
    private Long feedId;
    private String content;
    private List<String> postUrls;
}
