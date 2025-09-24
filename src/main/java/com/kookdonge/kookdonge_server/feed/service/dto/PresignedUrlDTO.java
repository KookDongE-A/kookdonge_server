package com.kookdonge.kookdonge_server.feed.service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class PresignedUrlDTO {
    private String presignedUrl;
    private String fileUrl;
    private String s3Key;
}
