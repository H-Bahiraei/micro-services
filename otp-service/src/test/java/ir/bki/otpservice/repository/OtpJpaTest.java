package ir.bki.otpservice.repository;

import ir.bki.otpservice.model.OtpEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 3/14/2022
 */
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class OtpJpaTest {

    @Autowired
    private OtpRepository otpRepository;

    @Autowired
    private TransactionTemplate transactionTemplate;

    static long ID = 1L;
    static String UUID = "XYZ";
    static String KEY = "ABC";
    static Long MOBILE_NO = 9133480144L;

    @Test
    public void shouldSaveAndLoadOrder() {
        Long otpId = transactionTemplate.execute(
                (ts) -> {
                    OtpEntity otpEntity = new OtpEntity();
                    otpEntity.setId(ID);
                    otpEntity.setUuid(UUID);
                    otpEntity.setKey(KEY.getBytes());
                    otpEntity.setMobileNo(MOBILE_NO);
                    otpRepository.save(otpEntity);
                    return otpEntity.getId();
                });
            transactionTemplate.execute((ts) -> {
            OtpEntity otp = otpRepository.findById(otpId).orElseThrow();
            assertEquals(ID, otp.getId());
            assertEquals(UUID, otp.getUuid());
            assertEquals(MOBILE_NO, otp.getMobileNo());
            return otp;
        });
    }

}


