//package ir.bki.otpservice.controller;
//
//import com.google.gson.Gson;
//import ir.bki.otpservice.IntegrationTest;
//import ir.bki.otpservice.model.NotificationRequestDto;
//import ir.bki.otpservice.model.ResponseDto;
//import ir.bki.otpservice.service.RedisHelperImpl;
//import ir.bki.otpservice.service.StrongAuthService;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import java.util.Set;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@IntegrationTest
//public class OtpTokenControllerTest {
//
//    //    public static final long TIME_OUT = Duration.ofMinutes(1).getSeconds();
//    public static final long TIME_OUT = 120;
//    private static final String API_URL = "/v1/otp";
//    private static final String AUTHORIZATION_HEADER = "HEADER";
//    private static final String PAIR_DATA = "3";
//    private static final int OTP_LENGTH = 5;
//    private static final String FAKE_HASH_KEY = "AAAAAAAAAA";
//    private static final String MESSAGE_BODY = "همراه بانک \n کد فعالسازی شما: \n ${code}";
//    private static final String WRONG_MOBILE_NUM = "9216017504";
//    final String SEPARATOR = ":";
//    private final String mobileNo = "989216017504";
//    @Autowired
//    StrongAuthService strongAuthService;
//    @Autowired
//    RedisHelperImpl redisHelper;
//    private int FAKE_Code = 12345;
//    @Autowired
//    private MockMvc mockMvc;
//    private int limitOfFA = 5;
//
//
//    @Test
//    public void sendOTPAndCheckRedisTest() throws Exception {
//
//        this.unBlock(mobileNo);
//
//// test 1 : generate otp and check key in redis
//        NotificationRequestDto notificationRequestDto = new NotificationRequestDto(MESSAGE_BODY);
//        MvcResult result = mockMvc.perform(
//                        post(API_URL + "/mobiles/" + mobileNo + "/generation")
//                                .contentType(APPLICATION_JSON_UTF8)
//                                .content(notificationRequestDto.toJSON())
//                                .header("Pair-Data", PAIR_DATA)
//                                .header("Timeout", TIME_OUT)
//                                .header("Otp-Length", OTP_LENGTH)
//                                .header("Authorization", AUTHORIZATION_HEADER))
//                .andExpect(status().isCreated()).andReturn();
//
//
//        String contentAsString = result.getResponse().getContentAsString();
//        ResponseDto<String> response = new Gson().fromJson(contentAsString, ResponseDto.class);
//
//        String randomStr = response.getPayload().get(0).substring(16, 26);
//        Set<String> redis_N = strongAuthService.getKeyesByPattern("HC" + SEPARATOR + mobileNo + SEPARATOR
//                + randomStr + SEPARATOR + "*****");
//        assertThat(redis_N.size()).isEqualTo(1);
//
//
//// test 2 : obtain the latest HC_Key by expire time and then check PAIR_DATA
//        Long largestExpTime = 0L;
//        String latestKey = "";
//        for (final String key : redis_N) {
//            if (largestExpTime < strongAuthService.getExpire(key)) {
//                largestExpTime = strongAuthService.getExpire(key);
//                latestKey = key;
//            }
//        }
//
//        assertEquals(strongAuthService.get(latestKey), PAIR_DATA);
//
//        assertThat(strongAuthService.get(latestKey)).isEqualTo(PAIR_DATA);
//
//        this.unBlock(mobileNo);
//    }
//
//
//    @Test
//    public void blockAndsendOTPAndCheckRedisTest() throws Exception {
//// test 3 :  block and call service generation OTP --> assert: BAD_REQ   and check size of redis
//
//        this.unBlock(mobileNo);
//
//        NotificationRequestDto notificationRequestDto = new NotificationRequestDto(MESSAGE_BODY);
//        MvcResult result = mockMvc.perform(
//                        post(API_URL + "/mobiles/" + mobileNo + "/generation")
//                                .contentType(APPLICATION_JSON_UTF8)
//                                .content(notificationRequestDto.toJSON())
//                                .header("Pair-Data", PAIR_DATA)
//                                .header("Timeout", TIME_OUT)
//                                .header("Otp-Length", OTP_LENGTH)
//                                .header("Authorization", AUTHORIZATION_HEADER))
//                .andExpect(status().isCreated()).andReturn();
//
//
//        int redis_I3 = getCountOfHCForANumPattern(mobileNo);
//        this.blockANum(mobileNo);
//        mockMvc.perform(
//                        post(API_URL + "/mobiles/" + mobileNo + "/generation")
//                                .header("Pair-Data", PAIR_DATA)
//                                .header("Timeout", TIME_OUT)
//                                .header("Otp-Length", OTP_LENGTH)
//                                .header("Authorization", AUTHORIZATION_HEADER))
//                .andExpect(status().isBadRequest());
//
//        int redis_N3 = getCountOfHCForANumPattern(mobileNo);
//        assertThat(redis_I3).isEqualTo(redis_N3);
//
//        this.unBlock(mobileNo);
//
//
//    }
//
//
//    // test 4: send invalid mobile num --> assert: Bad_Req  bayad benvisimesh
//    @Test
//    public void sendOTPByIncorrectPhoneNum() throws  Exception{
//
//
//        NotificationRequestDto notificationRequestDto = new NotificationRequestDto(MESSAGE_BODY);
//        mockMvc.perform(
//                        post(API_URL + "/mobiles/" + WRONG_MOBILE_NUM + "/generation")
//                                .contentType(APPLICATION_JSON_UTF8)
//                                .content(notificationRequestDto.toJSON())
//                                .header("Pair-Data", PAIR_DATA)
//                                .header("Timeout", TIME_OUT)
//                                .header("Otp-Length", OTP_LENGTH)
//                                .header("Authorization", AUTHORIZATION_HEADER))
//                .andExpect(status().isBadRequest());
//
//
//        int redis_I3 = getCountOfHCForANumPattern(WRONG_MOBILE_NUM);
//        assertThat(redis_I3).isEqualTo(0);
//
//    }
//
//    @Test
//    public void verifyOTPWithoutRecordInRedisTest() throws Exception {
//
//// test 1 : call verify while redis does not have its record --> assert : BAD_Req and check size od redis
//        this.unBlock(mobileNo);
//        int redis_I1 = getCountOfHCForANumPattern(mobileNo);
//        mockMvc.perform(
//                        post(API_URL + "/mobiles/" + mobileNo + "/verification")
//                                .header("Hash-Key", FAKE_HASH_KEY)
//                                .header("Pair-Data", PAIR_DATA)
//                                .header("Code", FAKE_Code)
//                                .header("Authorization", AUTHORIZATION_HEADER))
//                .andExpect(status().isBadRequest());
//
//        int redis_N1 = getCountOfHCForANumPattern(mobileNo);
//        assertThat(redis_I1).isEqualTo(redis_N1);
//
//        this.unBlock(mobileNo);
//
//
//    }
//
//    @Test
//    public void verifyOTPWithIncorrectHKeyTest() throws Exception {
//
//        this.unBlock(mobileNo);
//
//// test 2 and 3 : write on valid HC_key and call verify (assert --> BAD_Req and 200) and check redis before and after that ( assert --> 0)
//
//        String randomString = strongAuthService.generateStringRandom(10);
//        String clientHashKey = "HC" + SEPARATOR + mobileNo + SEPARATOR + randomString;
//        String cacheKey = clientHashKey + SEPARATOR + FAKE_Code;
//        strongAuthService.put(cacheKey, PAIR_DATA);
//
//        assertThat(strongAuthService.get(cacheKey)).isEqualTo(PAIR_DATA);
//
//        // BAD_Req : WHash-Key
//        mockMvc.perform(
//                        post(API_URL + "/mobiles/" + mobileNo + "/verification")
//                                .header("Hash-Key", "")
//                                .header("Pair-Data", PAIR_DATA)
//                                .header("Code", FAKE_Code)
//                                .header("Authorization", AUTHORIZATION_HEADER))
//                .andExpect(status().isBadRequest());
//
//        assertThat(strongAuthService.get(cacheKey)).isEqualTo(PAIR_DATA);
//
//        this.unBlock(mobileNo);
//
//    }
//
//
//    @Test
//    public void verifyOTPWithIncorrectPDTest() throws Exception {
//
//        this.unBlock(mobileNo);
//
//// test 2 and 3 : write on valid HC_key and call verify (assert --> BAD_Req ) and check redis before and after that ( assert --> 0)
//
//        String randomString = strongAuthService.generateStringRandom(10);
//        String clientHashKey = "HC" + SEPARATOR + mobileNo + SEPARATOR + randomString;
//        String cacheKey = clientHashKey + SEPARATOR + FAKE_Code;
//        strongAuthService.put(cacheKey, PAIR_DATA);
//
//        assertThat(strongAuthService.get(cacheKey)).isEqualTo(PAIR_DATA);
//
//        // BAD_Req : wrong PD
//        mockMvc.perform(
//                        post(API_URL + "/mobiles/" + mobileNo + "/verification")
//                                .header("Hash-Key", clientHashKey)
//                                .header("Pair-Data", "0")
//                                .header("Code", FAKE_Code)
//                                .header("Authorization", AUTHORIZATION_HEADER))
//                .andExpect(status().isBadRequest());
//
//        assertThat(strongAuthService.get(cacheKey)).isEqualTo(PAIR_DATA);
//
//        this.unBlock(mobileNo);
//
//    }
//
//    @Test
//    public void verifyOTPWithIncorrectOTPCodeTest() throws Exception {
//
//        this.unBlock(mobileNo);
//        String randomString = strongAuthService.generateStringRandom(10);
//        String clientHashKey = "HC" + SEPARATOR + mobileNo + SEPARATOR + randomString;
//        String cacheKey = clientHashKey + SEPARATOR + FAKE_Code;
//        strongAuthService.put(cacheKey, PAIR_DATA);
//
//        assertThat(strongAuthService.get(cacheKey)).isEqualTo(PAIR_DATA);
//
//        // BAD_Req : wrong Code
//
//
//        mockMvc.perform(
//                        post(API_URL + "/mobiles/" + mobileNo + "/verification")
//                                .header("Hash-Key", clientHashKey)
//                                .header("Pair-Data", PAIR_DATA)
//                                .header("Code", "11111")
//                                .header("Authorization", AUTHORIZATION_HEADER))
//                .andExpect(status().isBadRequest());
//
//        assertThat(strongAuthService.get(cacheKey)).isEqualTo(PAIR_DATA);
//
//        this.unBlock(mobileNo);
//
//    }
//
//    @Test
//    public void verifyOTPWithCorrectOTPCodeTest() throws Exception {
//
//        this.unBlock(mobileNo);
//        String randomString = strongAuthService.generateStringRandom(10);
//        String clientHashKey = "HC" + SEPARATOR + mobileNo + SEPARATOR + randomString;
//        String cacheKey = clientHashKey + SEPARATOR + FAKE_Code;
//        strongAuthService.put(cacheKey, PAIR_DATA);
//
//        assertThat(strongAuthService.get(cacheKey)).isEqualTo(PAIR_DATA);
//
//        // OK :
//        mockMvc.perform(
//                        post(API_URL + "/mobiles/" + mobileNo + "/verification")
//                                .header("Hash-Key", clientHashKey)
//                                .header("Pair-Data", PAIR_DATA)
//                                .header("Code", FAKE_Code)
//                                .header("Authorization", AUTHORIZATION_HEADER))
//                .andExpect(status().isOk());
//
//        assertThat(strongAuthService.get(cacheKey)).isEqualTo(null);
//
//        this.unBlock(mobileNo);
//    }
//
//
//    @Test
//    public void verifyAfterVerifyTest() throws Exception {
//
//        this.unBlock(mobileNo);
//        String randomString = strongAuthService.generateStringRandom(10);
//        String clientHashKey = "HC" + SEPARATOR + mobileNo + SEPARATOR + randomString;
//        String cacheKey = clientHashKey + SEPARATOR + FAKE_Code;
//        strongAuthService.put(cacheKey, PAIR_DATA);
//
//        assertThat(strongAuthService.get(cacheKey)).isEqualTo(PAIR_DATA);
//        // OK :
//        mockMvc.perform(
//                        post(API_URL + "/mobiles/" + mobileNo + "/verification")
//                                .header("Hash-Key", clientHashKey)
//                                .header("Pair-Data", PAIR_DATA)
//                                .header("Code", FAKE_Code)
//                                .header("Authorization", AUTHORIZATION_HEADER))
//                .andExpect(status().isOk());
//
//        assertThat(strongAuthService.get(cacheKey)).isEqualTo(null);
//
//// test 4 : after a success verify --> next verify --> assert : BAD_Req
//        mockMvc.perform(
//                        post(API_URL + "/mobiles/" + mobileNo + "/verification")
//                                .header("Hash-Key", clientHashKey)
//                                .header("Pair-Data", PAIR_DATA)
//                                .header("Code", FAKE_Code)
//                                .header("Authorization", AUTHORIZATION_HEADER))
//                .andExpect(status().isBadRequest());
//
//
//        this.unBlock(mobileNo);
//    }
//
//
//    @Test
//    public void verifyAfterBlockingTest() throws Exception {
//
//        this.unBlock(mobileNo);
//        String randomString = strongAuthService.generateStringRandom(10);
//        String clientHashKey = "HC" + SEPARATOR + mobileNo + SEPARATOR + randomString;
//        String cacheKey = clientHashKey + SEPARATOR + FAKE_Code;
//        strongAuthService.put(cacheKey, PAIR_DATA);
//
//        assertThat(strongAuthService.get(cacheKey)).isEqualTo(PAIR_DATA);
//// test 5 : after block call service verify ( assert --> BAD_Req )
//        this.blockANum(mobileNo);
//
//        strongAuthService.put(cacheKey, PAIR_DATA);
//
//        assertThat(strongAuthService.get(cacheKey)).isEqualTo(PAIR_DATA);
//
//        mockMvc.perform(
//                        post(API_URL + "/mobiles/" + mobileNo + "/verification")
//                                .header("Hash-Key", clientHashKey)
//                                .header("Pair-Data", PAIR_DATA)
//                                .header("Code", FAKE_Code)
//                                .header("Authorization", AUTHORIZATION_HEADER))
//                .andExpect(status().isBadRequest());
//
//        assertThat(strongAuthService.get(cacheKey)).isEqualTo(PAIR_DATA);
//        this.unBlock(mobileNo);
//
//
//    }
//
//
//    public void bluckingTest() throws Exception {
//
//        // isblock? assert : false
//
//        // wrong vrify then isnlock?  asset: false
//
//        // unblock : assert : 404
//
//        // wrong verify in count of limitaion and is blocking? assert: true
//
//        // unblock : assert : 200
//
//        // isblock? assert : false
//
//    }
//
//
//    private int getCountOfHCForANumPattern(String mobileNo) {
//        return strongAuthService.getKeyesByPattern("HC" + SEPARATOR + mobileNo + SEPARATOR + "**********" + SEPARATOR + "*****").size();
//    }
//
//    private void unBlock(String mobileNo) {
//        strongAuthService.deleteFailedAttemptByMobileNo(mobileNo);
//    }
//
//    public void blockANum(String mobileNo) {
//        for (int i = 0; i <= limitOfFA; i++) {
//            strongAuthService.createFailedAttempt(mobileNo);
//        }
//    }
//
//
//}
//
//
