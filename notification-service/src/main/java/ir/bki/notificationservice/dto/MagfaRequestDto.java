package ir.bki.notificationservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/3/2022
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public class MagfaRequestDto {

    List<String> messages;
    List<String> recipients;
    List<String> senders;
    List<String> uids;
    List<String> encodings;
    List<String> udhs;

}
