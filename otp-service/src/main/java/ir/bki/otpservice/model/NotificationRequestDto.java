package ir.bki.otpservice.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import ir.bki.otpservice.util.GsonModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/3/2022
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class NotificationRequestDto extends GsonModel {
    @NotEmpty(message = "NotEmpty is required")
//    @Pattern(regexp ="(.)*\\{code\\}(.)*"
//            , message = "Invalid message body ! , Pattern ")
    private String message;
    @JsonProperty("mobile_no")
    private String mobileNo;
    @JsonProperty("provider_no")
    private String providerNo;
    private String providerName;
    private String uid;
    private String encoding;
    private String udh;
    private Integer priority;

//    List<String> messages;
//    List<String>recipients;
//    List<String> senders;


    public NotificationRequestDto(String message, String mobileNo) {
        this.message = message;
        this.mobileNo = mobileNo;
    }
}
