package ir.bki.notificationservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import io.micrometer.core.instrument.binder.http.DefaultHttpServletRequestTagsProvider;
import ir.bki.notificationservice.utils.GsonModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/3/2022
 */
@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor

@RedisHash("NotificationRequest")
public class NotificationRequestDto extends GsonModel implements Serializable {
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
}
