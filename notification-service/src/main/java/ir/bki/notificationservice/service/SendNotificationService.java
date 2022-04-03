package ir.bki.notificationservice.service;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 3/26/2022
 */
public interface SendNotificationService {
    public long send(String mobileNo,String message);
    public long getBalance(String smsCenter);
}
