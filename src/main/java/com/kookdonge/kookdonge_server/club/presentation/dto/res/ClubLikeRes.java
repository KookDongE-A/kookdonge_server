package com.kookdonge.kookdonge_server.club.presentation.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ClubLikeRes {

    private boolean isLiked;

    public static ClubLikeRes of(boolean isLiked) {
        return new ClubLikeRes(isLiked);
    }
}