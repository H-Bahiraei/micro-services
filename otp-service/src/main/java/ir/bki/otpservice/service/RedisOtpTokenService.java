package ir.bki.otpservice.service;

import ir.bki.otpservice.OtpToken;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/5/2022
 */
public interface RedisOtpTokenService {
    OtpToken get(String id);

    void put(OtpToken otpToken);
}
