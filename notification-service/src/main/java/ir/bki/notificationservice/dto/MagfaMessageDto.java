package ir.bki.notificationservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/3/2022
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MagfaMessageDto {
   private Integer status;
   private Integer parts;
   private Float tariff;
   private String userId;
   private String recipient;
   private String alphabet;
   private Long id;
}
