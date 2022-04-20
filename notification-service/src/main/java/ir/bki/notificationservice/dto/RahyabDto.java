package ir.bki.notificationservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

/**
 * @author Mohsen Sabbaghi
 * @version 2022.1.1
 * https://www.linkedin.com/in/sabbaghi/
 * @since 4/3/2022
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RahyabDto {
    String username;
    String password;
    String number;
    String cellphones;
    String message;
    String udh;
    boolean farsi;
    boolean flash;
    boolean topic;
}
