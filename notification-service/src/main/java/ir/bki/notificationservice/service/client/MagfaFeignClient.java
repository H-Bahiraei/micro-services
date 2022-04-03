package ir.bki.notificationservice.service.client;

import ir.bki.notificationservice.dto.MagfaDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/3/2022
 */
//https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/
@FeignClient(name = "magfa", url = "https://sms.magfa.com", path = "/api/http/sms/v2")
public interface MagfaFeignClient {

    @RequestMapping(
            method= RequestMethod.GET,
            value="/balance",
            consumes="application/json")
    MagfaDto getBalance();

    @RequestMapping(
            method= RequestMethod.POST,
            value="/send",
            consumes="application/json",
            produces="application/json")
    MagfaDto send(MagfaDto magfaDto);
}
