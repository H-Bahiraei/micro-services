//package ir.bki.otpservice.redis;
//
//import org.hamcrest.BaseMatcher;
//import org.hamcrest.Description;
//import org.hamcrest.Matcher;
//import org.junit.jupiter.api.Test;
//
//import java.time.Duration;
//import java.util.Map;
//
//import static org.hamcrest.MatcherAssert.assertThat;
//import static org.hamcrest.Matchers.*;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertNull;
//
///**
// * @author Mahdi Sharifi
// * @version 2022.1.1
// * https://www.linkedin.com/in/mahdisharifi/
// * @since 3/26/2022
// */
//public class RedisTest {
//
//    @Test
//    void getPong() {
//        String pong = JedisWrapper.callPing();
//        assertEquals("PONG", pong);
//    }
//
//    @Test
//    public void addAndGet() {
//        JedisWrapper.set("KEY_A", "VALUE_A");
//        String dataRead = JedisWrapper.get("KEY_A");
//        assertThat(1L, lessThanOrEqualTo(JedisWrapper.dbSize()));
//        assertEquals("VALUE_A", dataRead);
//    }
//
//    @Test
//    public void addAndGetExpireTime() throws InterruptedException {
//        JedisWrapper.set("KEY_C", "VALUE_C", Duration.ofSeconds(3));
//        String dataRead = JedisWrapper.get("KEY_C");
//        assertEquals("VALUE_C", dataRead);
//
//        Thread.sleep(1000);
//        assertEquals("VALUE_C", dataRead);
//        Thread.sleep(2000);
//        String dataReadAfterTimeout = JedisWrapper.get("KEY_C");
//        assertNull( dataReadAfterTimeout,()->"#After Expire time data must be evicted!");
//
//    }
//
//    @Test
//    public void addAndGetHash() {
//        JedisWrapper.setHash("KEY_B", "NAME", "VALUE_B");
//        String dataRead = JedisWrapper.getHash("KEY_B", "NAME");
//        Map<String, String> map = JedisWrapper.getAllHash("KEY_B");
//
//        assertEquals("VALUE_B", dataRead);
//        assertThat(map, hasKey("NAME"));
//        assertThat(map, hasValue("VALUE_B"));
//        assertThat(map, hasEntry("NAME", "VALUE_B"));
//    }
//
//}
