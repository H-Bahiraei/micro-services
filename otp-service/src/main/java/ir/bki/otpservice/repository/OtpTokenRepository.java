package ir.bki.otpservice.repository;

import ir.bki.otpservice.OtpToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/5/2022
 */
@Repository
public interface OtpTokenRepository extends CrudRepository<OtpToken, String> {

}

