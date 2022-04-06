package ir.bki.notificationservice.dto;

import lombok.Data;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/3/2022
 */
@Data
public class MagfaMessageDto {
   private Integer status;
   private Integer parts;
   private Float tariff;
   private String userId;
   private String recipient;
   private String alphabet;
   private Long id;
}
