package ir.bki.otpservice.controller;

import ir.bki.otpservice.OtpToken;
import ir.bki.otpservice.service.RedisOtpTokenService;
import ir.bki.otpservice.service.RedisOtpTokenServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/5/2022
 */
@RestController
@RequestMapping("redis2")
public class RedisOtpTokenController {

    RedisOtpTokenServiceImpl redisOtpTokenService;

    public RedisOtpTokenController(RedisOtpTokenServiceImpl redisOtpTokenService) {
        this.redisOtpTokenService = redisOtpTokenService;
    }

    @GetMapping("{id}")
    public ResponseEntity<OtpToken> get(@PathVariable("id") String id) {
        return ResponseEntity.ok(redisOtpTokenService.get(id));
    }
    @GetMapping("put")
    public ResponseEntity<String> put() {
        System.out.println("####PUT");
        redisOtpTokenService.put(new OtpToken("1","MAHDI"));
        return ResponseEntity.ok("ADDED");
    }
}
