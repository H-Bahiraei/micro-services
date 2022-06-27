package ir.bki.otpservice.service.redis;

import ir.bki.otpservice.repository.model.CacheAuth;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/6/2022
 */
public interface StrongAuth {

    boolean isMobileBlock(String mobileNo) throws Exception;
    void unblockByDeletingFailedAttemptByMobileNo(String mobileNo);
    public Long getExpire(String key);

    int getCountOfFailedAttempts(String key);
    int findCodeAndKeyByHashFromCache(String hash, String codeFromReq);
    void createFailedAttempt(String mobileNo);
    int createCacheAuthInRedis(CacheAuth cacheAuth, long expiredTimeInMinutes);
    int deleteFailedAttemptByMobileNo(String key);
    int deleteFailedAttemptsByQueryInRedis(String key);
    int deleteCodeInRedis(String key);

    String correctMobileNo(String mobileNo)  ;
}
