package com.kookdonge.kookdonge_server.feed.presentation.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor(staticName = "of")
public class PresignedUrlListRes {
    private List<PresignedUrlRes> presignedUrlList;
}
