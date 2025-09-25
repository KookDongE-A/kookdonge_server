package com.kookdonge.kookdonge_server.feed.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.kookdonge.kookdonge_server.common.exception.CustomException;
import com.kookdonge.kookdonge_server.common.info.UserInfoStore;
import com.kookdonge.kookdonge_server.common.property.AwsS3Property;
import com.kookdonge.kookdonge_server.feed.common.FeedExceptionCode;
import com.kookdonge.kookdonge_server.feed.service.dto.PresignedUrlDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AwsS3Service {

    private final AmazonS3Client amazonS3Client;
    private final AwsS3Property awsS3Property;

    private static final long PRE_SIGNED_URL_EXPIRATION_TIME = 1000 * 60 * 5;

    // Presigned Url 단건 발급
    public PresignedUrlDTO generatePresignedUrl(String fileName, Long clubId) {
        Long userClubId = UserInfoStore.getClubId();
        if (userClubId == null || !userClubId.equals(clubId)) {
            throw new CustomException(FeedExceptionCode.FILE_ACCESS_DENIED);
        }

        String s3Key = generateKey(fileName, clubId);

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(
                awsS3Property.getS3().getBucket(), s3Key)
                .withMethod(HttpMethod.PUT)
                .withExpiration(new Date(System.currentTimeMillis() + PRE_SIGNED_URL_EXPIRATION_TIME));

        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString());

        String uploadUrl = amazonS3Client.generatePresignedUrl(generatePresignedUrlRequest).toString();
        String fileUrl = amazonS3Client.getUrl(awsS3Property.getS3().getBucket(), s3Key).toString();

        return PresignedUrlDTO.of(uploadUrl, fileUrl, s3Key);
    }

    // Presigned Url 다건 발급
    public List<PresignedUrlDTO> generatePresignedUrls(List<String> fileNames, Long clubId) {
        Long userClubId = UserInfoStore.getClubId();
        if (userClubId == null || !userClubId.equals(clubId)) {
            throw new CustomException(FeedExceptionCode.FILE_ACCESS_DENIED);
        }
        return fileNames.stream()
                .map(fileName -> generatePresignedUrl(fileName, clubId))
                .toList();
    }

    private String generateKey(String fileName, Long clubId) {
        int idx = fileName.lastIndexOf('.');
        String type = (idx != -1) ? fileName.substring(idx) : "";
        String uuid = UUID.randomUUID().toString().replace("-", "");

        return String.format(
                "club/%s/feeds/%s%s",
                clubId,
                uuid,
                type);
    }

}
