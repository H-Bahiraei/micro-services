package ir.bki.notificationservice.dto;

import lombok.Data;

import java.math.BigInteger;
import java.util.List;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/3/2022
 */
@Data
public class MagfaRequestDto {

    List<String> messages;
    List<String>recipients;
    List<String> senders;

}
