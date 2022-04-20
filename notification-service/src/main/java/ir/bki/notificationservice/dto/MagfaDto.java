package ir.bki.notificationservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MagfaDto {
    List<MagfaMessageDto> messages;
    List<String> recipients;
    List<String> senders;
    List<String> uids;
    List<String> encoding;
    List<String> udhs;
    private Integer status;
    private BigInteger balance;
    private Long id;
}
