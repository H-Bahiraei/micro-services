//package ir.bki.otpservice.redis;
//
//import ir.bki.otpservice.OtpToken;
//import ir.bki.otpservice.service.RedisService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
///**
// * @author Mahdi Sharifi
// * @version 2022.1.1
// * https://www.linkedin.com/in/mahdisharifi/
// * @since 4/5/2022
// */
//@SpringBootTest
//public class RedisIntegrationTest {
//
//    @Autowired
//    RedisService redisService;
//
//    @Test
//    public void test1(){
//        redisService.save(new OtpToken("1","Mahdi"));
//        System.out.println("# "+redisService.get(("1")));
//    }
//}
