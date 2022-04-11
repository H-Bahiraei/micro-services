package ir.bki.otpservice.service;

import ir.bki.otpservice.model.CacheAuth;
import ir.bki.otpservice.model.FailedAttempt;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;
import redis.clients.jedis.ScanParams;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/6/2022
 */
//https://developer.redis.com/develop/java/redis-and-spring-course/lesson_2/
//https://programmer.group/example-of-using-redis-in-spring-boot.html
// Key:Mobile -> (field:expireTime , value:otpCode)
// Key -> Value
@Service
public class StrongAuthService implements StrongAuth {
    public final static String keyword_Starter = "MF:";
    public final static String Hash_keyword_Starter = "HC:";
    public final static int CODE_LENGTH_DEFAULT =5 ;
    public final static int TIMEOUT_SECOND_DEFAULT = 5*60 ;

    public static final long SMS_AUTH_CODE_EXPIRE_MINUTE = 2; //2min bad code expire mishe
    public static final int COUNT_OF_FAILED_ATTEMPTS_HISTORY = 5; //a person must be blocked after 5 wrong pass


    private final RedisTemplate redisTemplate;
    private HashOperations hashOperations;

    @Autowired
    RedisHelperImpl redisHelper;



    public StrongAuthService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }
    public String generateCode() {
        return generateCode(CODE_LENGTH_DEFAULT );
    }
    public String generateCode(int length) {
        return RandomStringUtils.randomNumeric(length);//String.valueOf(new Random().nextInt(89999) + 10001);
    }
    public String generateStringRandom(int length) {
        return RandomStringUtils.randomAlphanumeric(length);
    }

    public Map<Object, Object> findAllOtpToken(String key) {
        return hashOperations.entries(key);
    }

    public void put(String key, String fields, String value) {
        hashOperations.put(key, fields, value);
    }
    public void put(String key, String value) {
        redisHelper.put(key, value);
    }

    public void del(String key, String field) {
        redisHelper.del(key, field);
    }
    public void del(String key) {
        redisHelper.del(key);
    }
    public void remove(String key) {
        redisHelper.remove(key);
    }
    public void delete(String key, String fields) {
        hashOperations.delete(key, fields);
    }
    public String get(String key, String field) {
        return (String) redisHelper.get(key, field);
    }
    public String get(String key) {
        return (String) redisHelper.get(key);
    }
    public String findOtpToken(String hashKey, String key) {
        return (String) hashOperations.get(hashKey, key);
    }
    public boolean expire(String key) {
        return redisTemplate.expire(key, Duration.ofSeconds(TIMEOUT_SECOND_DEFAULT));
    }
    public boolean expire(String key, Duration timeout) {
        return redisTemplate.expire(key, timeout);
    }
    public boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    @Override
    public int getCountOfFailedAttempts(String key) {
        int count = 0;
        if (redisHelper.exists(key)) {
            Map<String, String> failedAttemptHistory = redisHelper.getAllh(key);
            if (failedAttemptHistory != null && failedAttemptHistory.size() > 0) {
                long now = System.currentTimeMillis();
                count = failedAttemptHistory.size();
                for (Map.Entry<String, String> entry : failedAttemptHistory.entrySet()) {
                    long expireTime = Long.parseLong(entry.getKey());
                    if (now > expireTime) {
                        redisHelper.del(key, entry.getKey());
                        --count;
                    }
                }
            }
        }
        return count;
    }

    @Override
    public int findCodeAndKeyByHashFromCache(String hash, String otpCode) {
        CacheAuth cacheAuth = new CacheAuth();
        String hashAndCode = hash + ":" + otpCode;
        if (redisHelper.exists(hashAndCode)) {
            cacheAuth.setValue(otpCode);
            cacheAuth.setKey(redisHelper.get(hashAndCode));
        } else { //code or hash is incorrect
//            resultCode = ResultCodesEnum.SMS_AUTH_HASH_AND_CODE_NOT_FOUND;
        }
        return 0;
    }

    @Override
    public int createFailedAttempt(String hash, String codeFromReq) {
        FailedAttempt failedAttempt=new FailedAttempt();
        String key = failedAttempt.getMobileNo();
        String field = String.valueOf(failedAttempt.getExpireAt().getTime());
        String value = failedAttempt.getAppId() + ":" + failedAttempt.getOpCode();
        redisHelper.hashPut(key, field, value);
        return 0;
    }

    @Override
    public int createCacheAuthInRedis(CacheAuth cacheAuth, long expiredTimeInMinutes) {
        String key = cacheAuth.getHashKey() + ":" + cacheAuth.getValue();  //HC:989121234567:12345687jg:code
//        redisHelper.hashPut(key, cacheAuth.getKey());
        redisHelper.expire(key, (expiredTimeInMinutes * 60));
        return 0;
    }

    @Override
    public int deleteFailedAttemptByMobileNo(String key) {
        if (redisHelper.exists(key)) {
            redisHelper.remove(key);
        }
        return 0;
    }

    @Override
    public int deleteFailedAttemptsByQueryInRedis(String key) {
        ScanParams scanParams = new ScanParams();
//        scanParams.match(keyword_Starter + key + "*");


        String cursor = ScanParams.SCAN_POINTER_START;
        RedisConnection redisConnection = null;
        try {
            redisConnection = redisTemplate.getConnectionFactory().getConnection();
            ScanOptions options = ScanOptions.scanOptions().match(keyword_Starter + key + "*").count(1000).build();

            Cursor c = redisConnection.scan(options);
            while (c.hasNext()) {
//                logger.info(new String((byte[]) c.next()));
            }
        } finally {
            redisConnection.close(); //Ensure closing this connection.
        }
//        while (!cycleIsFinished) {
//            ScanResult<String> scanResult = jedis.scan(cursor, scanParams);
//            List<String> result = scanResult.getResult();
//            for (String key : result) {
//                jedis.del(key);
//            }
//            cursor = scanResult.getCursor();
//            if (cursor.equals("0")) {
//                cycleIsFinished = true;
//            }

        return 0;
    }

    @Override
    public int deleteCodeInRedis(String key) {
        if (redisHelper.exists(key)) { // Finally for certainity er delete count too
            redisHelper.del(key); // the event of CodeDeleteListener fires due to  "del" event of code and all prev failed attempts going to be deleted
//            LOGGER_REDIS.info("[" + String.format("%-5d", System.currentTimeMillis() - startCtm) + " ms] " + logHeader +
//                    "; code existed and deleted by hash in redis.");
        } else {
//            LOGGER_REDIS.warn("[" + String.format("%-5d", System.currentTimeMillis() - startCtm) + " ms] " + logHeader +
//                    "; hash: " + key + "; code didn't exist in redis.");
        }

        return 0;
    }
}
