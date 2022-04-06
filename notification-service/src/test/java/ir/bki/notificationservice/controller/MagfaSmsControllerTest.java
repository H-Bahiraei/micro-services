package ir.bki.notificationservice.controller;

import ir.bki.notificationservice.dto.MagfaDto;
import ir.bki.notificationservice.service.client.MagfaFeignClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.math.BigInteger;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/3/2022
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
class MagfaSmsControllerTest {

    @MockBean
    MagfaFeignClient magfaFeignClient;

    MagfaSmsController magfaSmsController;

    @BeforeEach
    void setUp() {
        MagfaDto magfaDto=new MagfaDto();
        magfaDto.setStatus(0);
        magfaDto.setBalance(new BigInteger(10000+""));
        Mockito.when(magfaFeignClient.getBalance()).thenReturn(magfaDto);
        magfaSmsController=new MagfaSmsController(magfaFeignClient);
    }

    @Test
    void getBalance() {
        assertEquals("10000",magfaSmsController.getBalance()+"");
    }

    @Test
    void send() {
        assertEquals("10000",magfaSmsController.send().getBalance()+"");
    }
}