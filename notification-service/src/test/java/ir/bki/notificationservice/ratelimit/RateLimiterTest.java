package ir.bki.notificationservice.ratelimit;

import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/4/2022
 */
//https://arnoldgalovics.com/feign-rate-limiter-resilience4j/
@SpringBootTest({"server.port:0"})
class RateLimiterTest {
//
//    @Autowired
//    private UserSessionClient userSessionClient;
//    @Test
//    public void testRateLimiterWorks() throws Exception {
//        String responseBody = "{ \"sessionId\": \"828bc3cb-52f0-482b-8247-d3db5c87c941\", \"valid\": true}";
//        String uuidString = "828bc3cb-52f0-482b-8247-d3db5c87c941";
//        UUID uuid = UUID.fromString(uuidString);
//        USER_SESSION_SERVICE.stubFor(get(urlPathEqualTo("/user-sessions/validate"))
//                .withQueryParam("sessionId", equalTo(uuidString))
//                .willReturn(aResponse().withBody(responseBody).withHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE).withFixedDelay(500)));
//    }
}