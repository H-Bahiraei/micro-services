package ir.bki.otpservice.repository;

import ir.bki.otpservice.model.OtpEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 3/14/2022
 */
@Repository
public interface OtpDatabaseRepository extends JpaRepository<OtpEntity,Long> {
}
