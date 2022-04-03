package ir.bki.otpservice.service;

import ir.bki.otpservice.model.OtpEntity;
import ir.bki.otpservice.repository.OtpRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 3/14/2022
 */
@Service
public class OtpServiceImpl implements OtpService{

    private final OtpRepository otpRepository;

    public OtpServiceImpl(OtpRepository otpRepository) {
        this.otpRepository = otpRepository;
    }

    @Override
    public List<OtpEntity> findAll() {
        return otpRepository.findAll();
    }

    @Override
    public OtpEntity findById(Long id) {
        return otpRepository.findById(id).orElseThrow();
    }
}
