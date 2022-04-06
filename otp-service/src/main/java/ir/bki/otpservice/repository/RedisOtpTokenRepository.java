package ir.bki.otpservice.repository;

import ir.bki.otpservice.OtpToken;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/5/2022
 */
@Repository
public interface RedisOtpTokenRepository {

    Map<Object, Object> findAllOtpToken();

    void add(OtpToken otpToken);

    void delete(String id);

    OtpToken findOtpToken(String id);
}
