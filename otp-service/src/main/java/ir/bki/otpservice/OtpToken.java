package ir.bki.otpservice;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/5/2022
 */
@RedisHash("OtpToken")
@Data
@AllArgsConstructor @NoArgsConstructor
public class OtpToken {
    private String id;
    private String name;
}
