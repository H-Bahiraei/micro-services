//package ir.bki.otpservice.service;
//
//import ir.bki.otpservice.OtpToken;
//import ir.bki.otpservice.repository.OtpTokenRepository;
//import org.springframework.stereotype.Service;
//
///**
// * @author Mahdi Sharifi
// * @version 2022.1.1
// * https://www.linkedin.com/in/mahdisharifi/
// * @since 4/5/2022
// */
//@Service
//public class RedisService {
//
//    private final OtpTokenRepository otpTokenRepository;
//
//    public RedisService(OtpTokenRepository otpTokenRepository) {
//        this.otpTokenRepository = otpTokenRepository;
//    }
//
//    public void save(OtpToken otpToken) {
//        otpTokenRepository.save(otpToken);
//    }
//
//    public OtpToken get(String key) {
//        OtpToken otpToken = otpTokenRepository.findById(key).get();
//        return otpToken;
//    }
//}
