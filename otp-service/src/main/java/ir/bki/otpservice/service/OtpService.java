package ir.bki.otpservice.service;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/6/2022
 */
public interface OtpService {

    boolean validate(String key, String value);

    String generate(String key);
}
