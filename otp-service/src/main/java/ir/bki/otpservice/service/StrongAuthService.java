package ir.bki.otpservice.service;

import ir.bki.otpservice.apects.Loggable;
import ir.bki.otpservice.model.CacheAuth;
import ir.bki.otpservice.model.FailedAttempt;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.time.Duration;
import java.util.Date;
import java.util.Map;
import java.util.Set;
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
    public final static int CODE_LENGTH_DEFAULT = 5;
    public final static int TIMEOUT_SECOND_DEFAULT = 5 * 60;

    public static final long SMS_AUTH_CODE_EXPIRE_MINUTE = 2; //2min bad code expire mishe
    public static final int COUNT_OF_FAILED_ATTEMPTS_HISTORY = 5; //a person must be blocked after 5 wrong pass
    private static int BLOCK_DURATION_IN_MINUTES = 60;


    private final RedisTemplate redisTemplate;
    @Autowired
    RedisHelperImpl redisHelper;
    private HashOperations hashOperations;


    public StrongAuthService(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOperations = redisTemplate.opsForHash();
    }

    public String generateCode() {
        return generateCode(CODE_LENGTH_DEFAULT);
    }

    public String generateCode(int length) {
//        return RandomStringUtils.randomNumeric(length);//String.valueOf(new Random().nextInt(89999) + 10001);

        final SecureRandom r = new SecureRandom();
        int min = 10;
        for (int i = 1; i < length - 1; i++)
            min *= 10;

        int max = min * 10 - 1;
        return (r.nextInt(max - min + 1) + min) + "";

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

    @Loggable
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


    public Set<String> getKeyesByPattern(String strPattern) {
        Set<String> keys = redisTemplate.keys(strPattern);
//        RedisTemplate<String, String> template = new RedisTemplate<>();
//        String keyPattern = strPattern+"*" ;
//        final byte[] k = template.getStringSerializer().serialize(keyPattern);

//        redisTemplate.setDefaultSerializer(new StringRedisSerializer());
//        return hashOperations.getOperations().keys(strPattern);
        return keys;
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

    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    public boolean expire(String key, long timeout, TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }


    @Loggable
    public void createFailedAttempt(String mobileNo) {
        Date now = new Date();
        FailedAttempt failedAttempt = new FailedAttempt();
        failedAttempt.setMobileNo(mobileNo);
        failedAttempt.setCreatedAt(now);
        failedAttempt.setExpireAt(DateUtils.addMinutes(now, BLOCK_DURATION_IN_MINUTES));

        String key = failedAttempt.getMobileNo(); //989133480144
        String field = String.valueOf(failedAttempt.getExpireAt().getTime()); // 1649677508626
        String value = "DON'T CARE"; //3655981:5522
        put(key, field, value);
//        System.out.println("****** Created FA");
    }

    @Override
    public int getCountOfFailedAttempts(String mobileNo) {
        int count = 0;
        if (redisHelper.exists(mobileNo)) {
            Map<String, String> failedAttemptHistory = redisHelper.getAllh(mobileNo);
            if (failedAttemptHistory != null && failedAttemptHistory.size() > 0) {
                long now = System.currentTimeMillis();
                count = failedAttemptHistory.size();
                for (Map.Entry<String, String> entry : failedAttemptHistory.entrySet()) {
                    long expireTime = Long.parseLong(entry.getKey());
                    if (now > expireTime) {
                        redisHelper.del(mobileNo, entry.getKey());
                        --count;
                    }
                }
            }
        }
        System.out.println("****count:" + count);
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
    public int createCacheAuthInRedis(CacheAuth cacheAuth, long expiredTimeInMinutes) {
        String key = cacheAuth.getHashKey() + ":" + cacheAuth.getValue();  //HC:989121234567:12345687jg:code
//        redisHelper.hashPut(key, cacheAuth.getKey());
        redisHelper.expire(key, (expiredTimeInMinutes * 60));
        return 0;
    }

    @Loggable
    @Override
    public int deleteFailedAttemptByMobileNo(String mobileNo) {
        if (redisHelper.exists(mobileNo)) {
            redisHelper.remove(mobileNo);
        }
        System.out.println("****** Delete All FA");
        return 0;
    }

    @Override
    public int deleteFailedAttemptsByQueryInRedis(String key) {
//        ScanParams scanParams = new ScanParams();
////        scanParams.match(keyword_Starter + key + "*");
//
//
//        String cursor = ScanParams.SCAN_POINTER_START;
//        RedisConnection redisConnection = null;
//        try {
//            redisConnection = redisTemplate.getConnectionFactory().getConnection();
//            ScanOptions options = ScanOptions.scanOptions().match(keyword_Starter + key + "*").count(1000).build();
//
//            Cursor c = redisConnection.scan(options);
//            while (c.hasNext()) {
////                logger.info(new String((byte[]) c.next()));
//            }
//        } finally {
//            redisConnection.close(); //Ensure closing this connection.
//        }
////        while (!cycleIsFinished) {
////            ScanResult<String> scanResult = jedis.scan(cursor, scanParams);
////            List<String> result = scanResult.getResult();
////            for (String key : result) {
////                jedis.del(key);
////            }
////            cursor = scanResult.getCursor();
////            if (cursor.equals("0")) {
////                cycleIsFinished = true;
////            }

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

    @Override
    public void unblockByDeletingFailedAttemptByMobileNo(String mobileNo) {

    }

    @Loggable
    @Override
    public boolean isMobileBlock(String mobileNo) {
        int countOfFailedAttempts = this.getCountOfFailedAttempts(mobileNo);
        if (countOfFailedAttempts >= 5) {
            return true;
        } else
            return false;
    }


    public String correctMobileNo(String mobileNo) {
        long mobileLong = Long.valueOf(mobileNo);
        if (mobileLong < 1000000000) {
            return "Invalid Mobile No length! input mobileNo:" + mobileNo;
        }
        mobileNo = mobileNo.trim();
        if (mobileNo.length() == 12 && mobileNo.substring(0, 2).equals("98"))
            return (mobileNo);
        else if (mobileNo.length() == 11 && mobileNo.substring(0, 1).equals("0"))
            return ("98" + mobileNo.substring(1));
        else {
            return "Invalid Mobile No .input mobileNos:" + mobileNo;
        }
    }

}
