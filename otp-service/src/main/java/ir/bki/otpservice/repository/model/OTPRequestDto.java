package ir.bki.otpservice.repository.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import ir.bki.otpservice.util.GsonModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
/**
 * @author H-Bahiraei
 * Created on 6/27/2022
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class OTPRequestDto extends GsonModel {
    @NotEmpty(message = "NotEmpty is required")
//    @Pattern(regexp ="(.)*\\{code\\}(.)*"
//            , message = "Invalid message body ! , Pattern ")
    private String message;
}
