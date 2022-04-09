package ir.bki.notificationservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/3/2022
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor @NoArgsConstructor
public class NotificationRequestDto {
     private String message;
//    List<String> messages;
//    List<String>recipients;
//    List<String> senders;

}
