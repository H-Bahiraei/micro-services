package ir.bki.otpservice.client;

import ir.bki.otpservice.apects.Loggable;
import ir.bki.otpservice.repository.model.NotificationRequestDto;
import ir.bki.otpservice.repository.model.ResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.ws.rs.QueryParam;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/9/2022
 */
//https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/
@FeignClient(name = "notification-service", url = "${notification-service.url}", path = "${notification-service.path}")
public interface NotificationServiceFeign {
//    @Loggable
    @RequestMapping(
            method = RequestMethod.POST,
            value = "/notification/{mobile-no}"
            , consumes = "application/json",
            produces = "application/json"
    )
    ResponseDto<String> send(@PathVariable("mobile-no") String mobileNo
                           , @RequestBody NotificationRequestDto notificationRequestDto);
//    (@PathVariable("mobile-no") String mobileNo,  @QueryParam("provider") String provider)
}
