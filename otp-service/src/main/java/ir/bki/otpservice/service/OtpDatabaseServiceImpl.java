package ir.bki.otpservice.service;

import ir.bki.otpservice.model.OtpEntity;
import ir.bki.otpservice.repository.OtpDatabaseRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 3/14/2022
 */
@Service
public class OtpDatabaseServiceImpl implements OtpDatabaseService {

    private final OtpDatabaseRepository otpDatabaseRepository;

    public OtpDatabaseServiceImpl(OtpDatabaseRepository otpDatabaseRepository) {
        this.otpDatabaseRepository = otpDatabaseRepository;
    }

    @Override
    public List<OtpEntity> findAll() {
        return otpDatabaseRepository.findAll();
    }

    @Override
    public OtpEntity findById(Long id) {
        return otpDatabaseRepository.findById(id).orElseThrow();
    }
}
