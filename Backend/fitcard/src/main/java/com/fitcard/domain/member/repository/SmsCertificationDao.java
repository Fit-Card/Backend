package com.fitcard.domain.member.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@RequiredArgsConstructor
@Repository
public class SmsCertificationDao {

    private final String PREFIX = "sms:";
    private final String FIN_PREFIX = "fin:";
    private final int LIMIT_TIME = 30 * 60;

    private final StringRedisTemplate redisTemplate;

    public void createSmsCertification(String phone, String certificationNumber) {
        redisTemplate.opsForValue()
                .set(PREFIX + phone, certificationNumber, Duration.ofSeconds(LIMIT_TIME));
    }

    public void createFinCertification(String phone, String certificationNumber) {
        redisTemplate.opsForValue()
                .set(FIN_PREFIX + phone, certificationNumber);
    }

    public String getSmsCertification(String phone) {
        return redisTemplate.opsForValue().get(PREFIX + phone);
    }

    public String getFinCertification(String phone) {
        return redisTemplate.opsForValue().get(FIN_PREFIX + phone);
    }

    public void removeSmsCertification(String phone) {
        redisTemplate.delete(PREFIX + phone);
    }

    public boolean hasKey(String phone) {
        return redisTemplate.hasKey(PREFIX + phone);
    }

}