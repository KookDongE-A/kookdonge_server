package com.kookdonge.kookdonge_server.club.service;

import com.kookdonge.kookdonge_server.club.infra.jpa.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.AbstractMap;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClubStatsService {

    private final RedisTemplate<String, Object> redisTemplate;
    private final ClubRepository clubRepository;

    private static final String VIEW_KEY_PREFIX = "club:view:";
    private static final String LIKE_KEY_PREFIX = "club:like:";
    private static final String WEEKLY_VIEW_KEY = "club:weekly:view";
    private static final String WEEKLY_LIKE_KEY = "club:weekly:like";
    private static final long VIEW_DUPLICATE_PREVENT_HOURS = 1;

    @Transactional
    public boolean incrementViewCount(Long clubId, Long userId) {
        String viewKey = VIEW_KEY_PREFIX + clubId + ":" + userId;

        Boolean isViewed = redisTemplate.opsForValue().setIfAbsent(viewKey, "1", VIEW_DUPLICATE_PREVENT_HOURS, TimeUnit.HOURS);

        if (Boolean.TRUE.equals(isViewed)) {
            redisTemplate.opsForHash().increment(WEEKLY_VIEW_KEY, clubId.toString(), 1);
            clubRepository.incrementTotalViewCount(clubId, 1L);
            return true;
        }
        return false;
    }

    public boolean toggleLike(Long clubId, Long userId) {
        String likeKey = LIKE_KEY_PREFIX + clubId;
        String userIdStr = userId.toString();

        Boolean isMember = redisTemplate.opsForSet().isMember(likeKey, userIdStr);

        if (Boolean.TRUE.equals(isMember)) {
            redisTemplate.opsForSet().remove(likeKey, userIdStr);
            redisTemplate.opsForHash().increment(WEEKLY_LIKE_KEY, clubId.toString(), -1);
            return false;
        } else {
            redisTemplate.opsForSet().add(likeKey, userIdStr);
            redisTemplate.opsForHash().increment(WEEKLY_LIKE_KEY, clubId.toString(), 1);
            return true;
        }
    }

    public boolean isLiked(Long clubId, Long userId) {
        String likeKey = LIKE_KEY_PREFIX + clubId;
        return Boolean.TRUE.equals(redisTemplate.opsForSet().isMember(likeKey, userId.toString()));
    }

    public Long getWeeklyViewCount(Long clubId) {
        Object count = redisTemplate.opsForHash().get(WEEKLY_VIEW_KEY, clubId.toString());
        return count != null ? Long.parseLong(count.toString()) : 0L;
    }

    public Long getWeeklyLikeCount(Long clubId) {
        Object count = redisTemplate.opsForHash().get(WEEKLY_LIKE_KEY, clubId.toString());
        return count != null ? Long.parseLong(count.toString()) : 0L;
    }

    public List<Map.Entry<Long, Long>> getTopClubsByWeeklyView(int limit) {
        return getTopClubsByKey(WEEKLY_VIEW_KEY, limit);
    }

    public List<Map.Entry<Long, Long>> getTopClubsByWeeklyLike(int limit) {
        return getTopClubsByKey(WEEKLY_LIKE_KEY, limit);
    }

    private List<Map.Entry<Long, Long>> getTopClubsByKey(String redisKey, int limit) {
        Map<Object, Object> counts = redisTemplate.opsForHash().entries(redisKey);

        return counts.entrySet().stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(
                        Long.parseLong(entry.getKey().toString()),
                        Long.parseLong(entry.getValue().toString())
                ))
                .sorted(Comparator.comparing(Map.Entry<Long, Long>::getValue).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveWeeklyStatsToDatabase() {
        Map<Object, Object> weeklyLikes = redisTemplate.opsForHash().entries(WEEKLY_LIKE_KEY);

        weeklyLikes.forEach((clubId, count) -> {
            Long id = Long.parseLong(clubId.toString());
            Long likeCount = Long.parseLong(count.toString());
            if (likeCount > 0) {
                clubRepository.incrementTotalLikeCount(id, likeCount);
            }
        });
    }

    public void resetWeeklyStats() {
        redisTemplate.delete(WEEKLY_VIEW_KEY);
        redisTemplate.delete(WEEKLY_LIKE_KEY);
    }
}
