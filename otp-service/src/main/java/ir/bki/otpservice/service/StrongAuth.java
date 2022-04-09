package ir.bki.otpservice.service;

import ir.bki.otpservice.model.CacheAuth;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/6/2022
 */
public interface StrongAuth {
    int getCountOfFailedAttempts(String key);
    int findCodeAndKeyByHashFromCache(String hash, String codeFromReq);
    int createFailedAttempt(String hash, String codeFromReq);
    int createCacheAuthInRedis(CacheAuth cacheAuth, long expiredTimeInMinutes);
    int deleteFailedAttemptByMobileNo(String key);
    int deleteFailedAttemptsByQueryInRedis(String key);
    int deleteCodeInRedis(String key);
}
