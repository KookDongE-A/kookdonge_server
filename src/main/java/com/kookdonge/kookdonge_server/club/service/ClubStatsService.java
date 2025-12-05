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
    private static final String WEEKLY_VIEW_KEY = "club:weekly:view";
    private static final long VIEW_DUPLICATE_PREVENT_HOURS = 1;

    @Transactional
    public boolean incrementViewCount(Long clubId, Long userId, String ipAddress, String userAgent) {
        String identifier;

        if (userId != null) {
            // 로그인 유저: userId 사용
            identifier = "user:" + userId;
        } else {
            // 비로그인 유저: IP + User-Agent 해시 사용
            String fingerprint = ipAddress + ":" + userAgent;
            String hash = generateHash(fingerprint);
            identifier = "guest:" + hash;
        }

        String viewKey = VIEW_KEY_PREFIX + clubId + ":" + identifier;

        Boolean isViewed = redisTemplate.opsForValue().setIfAbsent(viewKey, "1", VIEW_DUPLICATE_PREVENT_HOURS, TimeUnit.HOURS);

        if (Boolean.TRUE.equals(isViewed)) {
            redisTemplate.opsForHash().increment(WEEKLY_VIEW_KEY, clubId.toString(), 1);
            clubRepository.incrementTotalViewCount(clubId, 1L);
            return true;
        }
        return false;
    }

    private String generateHash(String input) {
        return org.springframework.util.DigestUtils.md5DigestAsHex(input.getBytes(java.nio.charset.StandardCharsets.UTF_8));
    }

    public Long getWeeklyViewCount(Long clubId) {
        Object count = redisTemplate.opsForHash().get(WEEKLY_VIEW_KEY, clubId.toString());
        return count != null ? Long.parseLong(count.toString()) : 0L;
    }

    public List<Map.Entry<Long, Long>> getTopClubsByWeeklyView(int limit) {
        Map<Object, Object> counts = redisTemplate.opsForHash().entries(WEEKLY_VIEW_KEY);

        return counts.entrySet().stream()
                .map(entry -> new AbstractMap.SimpleEntry<>(
                        Long.parseLong(entry.getKey().toString()),
                        Long.parseLong(entry.getValue().toString())
                ))
                .sorted(Comparator.comparing(Map.Entry<Long, Long>::getValue).reversed())
                .limit(limit)
                .collect(Collectors.toList());
    }

    public void resetWeeklyStats() {
        redisTemplate.delete(WEEKLY_VIEW_KEY);
    }
}
