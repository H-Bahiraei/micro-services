package ir.bki.otpservice.repository;

import ir.bki.otpservice.OtpToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/5/2022
 */
//https://dzone.com/articles/intro-to-redis-with-spring-boot
@Repository
public class RedisOtpTokenRepositoryImpl implements RedisOtpTokenRepository {
    private static final String KEY = "Otp";

    private final RedisTemplate<String, Object> redisTemplate;
    private HashOperations hashOperations;

    @Autowired
    public RedisOtpTokenRepositoryImpl(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public Map<Object, Object> findAllOtpToken() {
        return hashOperations.entries(KEY);
    }

    @Override
    public void add(final OtpToken otpToken) {
        hashOperations.put(KEY, otpToken.getId(), otpToken.getName());
    }

    @Override
    public void delete(String id) {
        hashOperations.delete(KEY, id);
    }

    @Override
    public OtpToken findOtpToken(String id) {
        return (OtpToken) hashOperations.get(KEY, id);
    }

}