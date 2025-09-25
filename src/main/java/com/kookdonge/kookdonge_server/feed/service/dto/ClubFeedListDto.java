package com.kookdonge.kookdonge_server.feed.service.dto;

import com.kookdonge.kookdonge_server.feed.presentation.dto.res.ClubFeedRes;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(staticName = "of")
public class ClubFeedListDto {
    private List<ClubFeedDto> clubFeedList;
}
