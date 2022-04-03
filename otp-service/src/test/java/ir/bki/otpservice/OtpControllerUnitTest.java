package ir.bki.otpservice;

import ir.bki.otpservice.controller.OtpController;
import ir.bki.otpservice.model.OtpEntity;
import ir.bki.otpservice.service.OtpService;
import ir.bki.otpservice.service.OtpServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 3/14/2022
 */
@ExtendWith(MockitoExtension.class)
//@ExtendWith(SpringExtension.class)
//@SpringBootTest
public class OtpControllerUnitTest {

//    @MockBean
//    @Autowired
//    private OtpServiceImpl otpService;

    @BeforeAll
    public static void setup(){
        System.out.println("setup");
        List<OtpEntity> list=new ArrayList<>();
        OtpEntity otpEntity1=new OtpEntity();
        otpEntity1.setId(1L); otpEntity1.setMobileNo(9133480144L);
        list.add(otpEntity1);
        OtpEntity otpEntity2=new OtpEntity();
        otpEntity2.setId(2L); otpEntity2.setMobileNo(9122480111L);
        list.add(otpEntity2);
        OtpServiceImpl otpService = Mockito.mock(OtpServiceImpl.class);
        Mockito.when(otpService.findAll()).thenReturn(list);

        OtpController otpController=new OtpController(otpService);
        System.out.println(otpController.findAll());
    }

    @Test
    void findAll(){
        System.out.println("TEST findAll");
//        OtpController otpController=new OtpController(otpService);
//        otpController.findAll();
    }
}
