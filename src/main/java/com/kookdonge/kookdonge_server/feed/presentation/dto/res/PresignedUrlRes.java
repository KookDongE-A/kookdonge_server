package com.kookdonge.kookdonge_server.feed.presentation.dto.res;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class PresignedUrlRes {
    private String presignedUrl;
    private String fileUrl;
    private String s3Key;
}
