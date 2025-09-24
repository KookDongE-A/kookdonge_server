package com.kookdonge.kookdonge_server.feed.presentation;

import com.kookdonge.kookdonge_server.common.dto.RequestDTO;
import com.kookdonge.kookdonge_server.common.dto.ResponseDTO;
import com.kookdonge.kookdonge_server.feed.presentation.dto.req.FeedCreatedReq;
import com.kookdonge.kookdonge_server.feed.presentation.dto.req.PresignedUrlListReq;
import com.kookdonge.kookdonge_server.feed.presentation.dto.req.PresignedUrlReq;
import com.kookdonge.kookdonge_server.feed.presentation.dto.res.ClubFeedListRes;
import com.kookdonge.kookdonge_server.feed.presentation.dto.res.ClubFeedRes;
import com.kookdonge.kookdonge_server.feed.presentation.dto.res.PresignedUrlListRes;
import com.kookdonge.kookdonge_server.feed.presentation.dto.res.PresignedUrlRes;
import com.kookdonge.kookdonge_server.feed.service.AwsS3Service;
import com.kookdonge.kookdonge_server.feed.service.FeedService;
import com.kookdonge.kookdonge_server.feed.service.dto.PresignedUrlDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "피드")
@RequiredArgsConstructor
@RestController
public class FeedPresentation {

    private final AwsS3Service awsS3Service;
    private final FeedService feedService;

    @Operation(summary = "피드 생성")
    @PostMapping("/api/feeds")
    public ResponseDTO<Void> createFeed(
            @RequestParam("club") Long clubId,
            @Valid @RequestBody RequestDTO<FeedCreatedReq> feedCreatedReq
    ){
        feedService.createFeed(clubId, feedCreatedReq.getData());
        return ResponseDTO.ok();
    }

    @Operation(summary = "클럽 피드 목록 조회")
    @GetMapping("/api/feeds")
    public ResponseDTO<ClubFeedListRes> getFeedList(
            @RequestParam("club") Long clubId
    ){
        ClubFeedListRes clubFeedListRes = feedService.getFeedList(clubId);
        return ResponseDTO.ok(clubFeedListRes);
    }

    @Operation(summary = "클럽 피드 상세 조회")
    @GetMapping("/api/feeds/{feedId}")
    public ResponseDTO<ClubFeedRes> getFeed(
            @PathVariable Long feedId
    ){
        return ResponseDTO.ok(feedService.getFeed(feedId));
    }

    @Operation(summary = "피드 이미지 업로드를 위한 Presigned Url 발급")
    @PostMapping("/api/presigned-urls")
    public ResponseDTO<PresignedUrlListRes> generatePresignedUrls (
            @RequestParam("club") Long clubId,
            @Valid @RequestBody RequestDTO<PresignedUrlListReq> presignedUrlListReq
    ) {
        PresignedUrlListReq payload = presignedUrlListReq.getData();

        List<PresignedUrlDTO> presignedUrlDTOList = awsS3Service.generatePresignedUrls(
                payload.getPresignedUrlList().stream()
                        .map(PresignedUrlReq::getFileName)
                        .toList(),
                clubId
        );

        List<PresignedUrlRes> urlResList = presignedUrlDTOList.stream()
                .map(dto -> PresignedUrlRes.of(
                        dto.getPresignedUrl(),
                        dto.getFileUrl(),
                        dto.getS3Key()
                ))
                .toList();

        return ResponseDTO.ok(PresignedUrlListRes.of(urlResList));
    }

//    단건 presigned url 생성시 필요
//    @PostMapping("/api/presigned-url")
//    public ResponseDTO<PresignedUrlRes> generatePresignedUrl (
//             @Valid @RequestBody ResponseDTO<PresignedUrlReq> presignedUrlReq) {
//        PresignedUrlReq payload = presignedUrlReq.getData();
//        String fileName = payload.getFileName();
//
//        PresignedUrlDTO presignedUrlDTO = awsS3Service.generatePresignedUrl(fileName);
//
//        return ResponseDTO.ok(PresignedUrlRes.of(
//                presignedUrlDTO.getPresignedUrl(),
//                presignedUrlDTO.getFileUrl(),
//                presignedUrlDTO.getS3Key()
//        ));
//    }
}
