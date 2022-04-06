package ir.bki.otpservice.service;

import ir.bki.otpservice.OtpToken;
import ir.bki.otpservice.repository.OtpTokenRepository;
import ir.bki.otpservice.repository.RedisOtpTokenRepository;
import org.springframework.stereotype.Service;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/5/2022
 */
@Service
public class RedisOtpTokenServiceImpl implements RedisOtpTokenService {

//    private final RedisOtpTokenRepository redisOtpTokenRepository;
    private final OtpTokenRepository redisOtpTokenRepository;

    public RedisOtpTokenServiceImpl(OtpTokenRepository redisOtpTokenRepository) {
        this.redisOtpTokenRepository = redisOtpTokenRepository;
    }


    @Override
    public OtpToken get(String id) {
        return redisOtpTokenRepository.findById(id).orElseThrow();
    }

    @Override
    public void put(OtpToken otpToken) {
        redisOtpTokenRepository.save(otpToken);
    }
}
