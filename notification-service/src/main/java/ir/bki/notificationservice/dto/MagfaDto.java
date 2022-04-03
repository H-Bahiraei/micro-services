package ir.bki.notificationservice.dto;

import lombok.Data;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/3/2022
 */
@Data
public class MagfaDto {
    private int status;
    private BigInteger balance;
    List<String> messages;
    List<String>recipients;
    List<String>senders;
    List<String>uids;
    List<String>encoding;
    List<String>udhs;
}
