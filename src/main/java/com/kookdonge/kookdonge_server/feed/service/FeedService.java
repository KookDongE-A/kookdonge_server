package com.kookdonge.kookdonge_server.feed.service;

import com.kookdonge.kookdonge_server.common.CustomException;
import com.kookdonge.kookdonge_server.common.UserInfoStore;
import com.kookdonge.kookdonge_server.feed.common.FeedExceptionCode;
import com.kookdonge.kookdonge_server.feed.infra.jpa.entity.FeedEntity;
import com.kookdonge.kookdonge_server.feed.infra.jpa.repository.FeedRepository;
import com.kookdonge.kookdonge_server.feed.presentation.dto.req.FeedCreatedReq;
import com.kookdonge.kookdonge_server.feed.presentation.dto.res.ClubFeedListRes;
import com.kookdonge.kookdonge_server.feed.presentation.dto.res.ClubFeedRes;
import com.kookdonge.kookdonge_server.feedpost.infra.jpa.entity.FeedPostEntity;
import com.kookdonge.kookdonge_server.feedpost.infra.jpa.repository.FeedPostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FeedService {

    private final FeedRepository feedRepository;
    private final FeedPostRepository feedPostRepository;

    @Transactional
    public void createFeed(Long clubId, FeedCreatedReq feedCreatedReq) {
        Long userClubId = UserInfoStore.getClubId();
        if (userClubId == null || !userClubId.equals(clubId)) {
            throw new CustomException(FeedExceptionCode.FEED_ACCESS_DENIED);
        }

        FeedEntity feedEntity = FeedEntity.ofDB(clubId, feedCreatedReq.getContent());
        feedRepository.save(feedEntity);

        feedCreatedReq.getPostUrls().forEach(PostUrlReq -> {
            FeedPostEntity feedPostEntity = FeedPostEntity.ofDB(PostUrlReq.getPostUrl(), feedEntity.getFeedId());
            feedPostRepository.save(feedPostEntity);
        });
    }

    public ClubFeedListRes getFeedList(Long clubId) {
        List<FeedEntity> feedEntityList = feedRepository.findAllByClubIdOrderByCreatedAtDesc(clubId);
        if (feedEntityList.isEmpty()) {
            throw new CustomException(FeedExceptionCode.CLUB_FEED_NOT_FOUND);
        }

        List<ClubFeedRes> clubFeedList = feedEntityList.stream().map(feedEntity -> {
            List<String> postUrls = feedPostRepository.findAllByFeedId(feedEntity.getFeedId())
                    .stream()
                    .map(FeedPostEntity::getPostUrl)
                    .toList();
            return ClubFeedRes.of(feedEntity.getFeedId(), feedEntity.getContent(), postUrls);
        }).toList();

        return ClubFeedListRes.of(clubFeedList);
    }

    public ClubFeedRes getFeed(Long feedId){
        FeedEntity feedEntity = feedRepository.findByFeedId(feedId)
                .orElseThrow(() -> new CustomException(FeedExceptionCode.FEED_NOT_FOUND));

        List<String> postUrls = feedPostRepository.findAllByFeedId(feedId)
                .stream()
                .map(FeedPostEntity::getPostUrl)
                .toList();

        return ClubFeedRes.of(feedEntity.getFeedId(), feedEntity.getContent(), postUrls);
    }
}
