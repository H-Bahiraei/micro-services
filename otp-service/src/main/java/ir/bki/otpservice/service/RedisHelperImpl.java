package ir.bki.otpservice.service;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/6/2022
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

//https://programmer.group/example-of-using-redis-in-spring-boot.html
@Service("RedisHelper")
public class RedisHelperImpl implements RedisHelper {
    // Get redisTemplate instance in constructor, key(not hashKey) uses String type by default
    private final RedisTemplate redisTemplate;
    // Instantiate the operation object in the constructor through the redisTemplate factory method
    private final HashOperations<String, String, String> hashOperations;
    private final ListOperations<String, String> listOperations;
    private final ValueOperations<String, String> valueOperations;


    // IDEA can be injected successfully even though it has errors. After instantiating the operation object, the method can be called directly to operate the Redis database
    @Autowired
    public RedisHelperImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
        this.hashOperations = redisTemplate.opsForHash();
        this.listOperations = redisTemplate.opsForList();
//        this.zSetOperations = redisTemplate.opsForZSet();
//        this.setOperations = redisTemplate.opsForSet();
        this.valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public void hashPut(String key, String field, String value) {
        hashOperations.put(key, field, value);
    }

    public long hashSize(String key) {
        return hashOperations.size(key);
    }

    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    @Override
    public Map<String, String> getAllh(String key) {
        return hashOperations.entries(key);
    }

    @Override
    public String get(String key, String field) {
        return hashOperations.get(key, field);
    }

    @Override
    public void del(String key, String field) {
        hashOperations.delete(key, field);
    }
    @Override
    public void del(String key) {
        hashOperations.delete(key);
    }
    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }

    @Override
    public List<String> listFindAll(String key) {
        if (!redisTemplate.hasKey(key)) {
            return null;
        }
        return listOperations.range(key, 0, listOperations.size(key));
    }

    @Override
    public void put(String key, String value) {
        valueOperations.set(key, value);
    }

    @Override
    public String get(String key) {
        return valueOperations.get(key);
    }


    @Override
    public boolean expire(String key, long timeout) {
        return redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
    }
    @Override
    public boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }
    @Override
    public Long listPush(String key, String domain) {
        return listOperations.rightPush(key, domain);
    }

    @Override
    public Long listUnshift(String key, String domain) {
        return listOperations.leftPush(key, domain);
    }
    @Override
    public String listLPop(String key) {
        return listOperations.leftPop(key);
    }

}