//package ir.bki.otpservice.redis;
//
//import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
//
//import ir.bki.otpservice.service.redis.RedisHelperImpl;
//import ir.bki.otpservice.service.redis.StrongAuthService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.data.redis.core.StringRedisTemplate;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//
///**
// * @author Mahdi Sharifi
// * @version 2022.1.1
// * https://www.linkedin.com/in/mahdisharifi/
// * @since 4/6/2022
// */
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
//public class TestOnRedisOpetarions {
//
//    @Autowired
//    private StringRedisTemplate stringRedisTemplate;
//
//    @Autowired
//    private RedisTemplate<String, Object> redisTemplate;
//
//    @Autowired
//    private RedisHelperImpl redisHelper;
//
//    @Autowired
//    private StrongAuthService strongAuthService;
//
//
//    @Test
//    public void FailedAttemptInRedisTest() throws Exception {
//
//        strongAuthService.deleteFailedAttemptByMobileNo("09216017504");
//        strongAuthService.createFailedAttempt("09216017504");
//        strongAuthService.createFailedAttempt("09216017504");
//        assertThat(strongAuthService.getCountOfFailedAttempts("09216017504")).isEqualTo(2);
//        strongAuthService.deleteFailedAttemptByMobileNo("09216017504");
//
//
//
////
////        Author user = new Author();
////        user.setName("Alex");
////        user.setIntro_l("A program that doesn't play basketball is not a good man");
////
////        // Save data like redis
////        redisHelper.put("aaa", "user");
////
////        // Getting cached data from key
////        String autor = (String) redisHelper.get("aaa");
////
////        // Delete a data based on the key
////        //redisHelper.remove("aaa");
////        System.out.println(autor);
////
////        redisHelper.hashPut("989133480144","1","value1");
////        redisHelper.hashPut("989133480144","11","value11");
////        redisHelper.hashPut("989133480144","2","value2");
////        if(redisHelper.exists("989133480144")) System.out.println("EXIST -> 989133480144");
////        System.out.println(redisHelper.get("989133480144","1"));
////        System.out.println("hashSize: "+redisHelper.hashSize("989133480144"));
////        Map<String, String> map=redisHelper.getAllh("989133480144");
////        map.forEach((x,y)->{
////            System.out.println(x+"->"+y);
////        });
//    }
////
////    @Test
////    public void testObj() throws Exception {
////        Author user = new Author();
////        user.setName("Jerry");
////        user.setIntro_l("A program that doesn't play basketball is not a good man!");
////
////        ValueOperations<String, Author> operations = redisTemplate.opsForValue();
////        operations.set("502", user);
////        Thread.sleep(500);
////        boolean exists = redisTemplate.hasKey("502");
////        if (exists) {
////            System.out.println(redisTemplate.opsForValue().get("502"));
////        } else {
////            System.out.println("exists is false");
////        }
////    }
//}
