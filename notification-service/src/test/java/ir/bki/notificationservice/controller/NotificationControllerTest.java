//package ir.bki.notificationservice.controller;
//
//import ir.bki.notificationservice.dto.MagfaResponseDto;
//import ir.bki.notificationservice.service.client.MagfaFeignClient;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mockito;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//
//import java.math.BigInteger;
//
///**
// * @author Mahdi Sharifi
// * @version 2022.1.1
// * https://www.linkedin.com/in/mahdisharifi/
// * @since 4/3/2022
// */
//@ExtendWith(MockitoExtension.class)
//@SpringBootTest
//class MagfaSmsControllerTest {
//
//    @MockBean
//    MagfaFeignClient magfaFeignClient;
//
//    NotificationController notificationController;
//
//    @BeforeEach
//    void setUp() {
//        MagfaResponseDto magfaDto = new MagfaResponseDto();
//        magfaDto.setStatus(0);
//        magfaDto.setBalance(new BigInteger(10000 + ""));
//        Mockito.when(magfaFeignClient.getBalance()).thenReturn(magfaDto);
//        notificationController = new NotificationController(magfaFeignClient);
//    }
//
////    @Test
////    void getBalance() {
////        assertEquals("10000",magfaSmsController.getBalance().getBody()+"");
////    }
//
////    @Test
////    void send() {
////        assertEquals("10000",magfaSmsController.send().getBalance()+"");
////    }
//}