package com.financial.domain.fin.user.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class FinUserCertificationDao {
    private final String PREFIX = "fin:";
    private final int LIMIT_TIME = 30 * 60;

    private final StringRedisTemplate redisTemplate;

    public String getSmsCertification(String phone) {
        return redisTemplate.opsForValue().get(PREFIX + phone);
    }

    public boolean hasKey(String phone) {
        return redisTemplate.hasKey(PREFIX + phone);
    }
}
