package com.kookdonge.kookdonge_server.waitinglist.service.dto;

import com.kookdonge.kookdonge_server.club.infra.jpa.entity.ClubType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class ClubInWaitingListDto {
    private Long clubId;
    private String clubName;
    private String clubProfileImageUrl;
    private ClubType clubType;
}
