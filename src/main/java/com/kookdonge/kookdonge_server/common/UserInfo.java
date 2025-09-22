package com.kookdonge.kookdonge_server.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class UserInfo {
    private Long userId;
    private Long clubId;
}
