package ir.bki.notificationservice.service.client;

import feign.Headers;
import ir.bki.notificationservice.dto.RahyabDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Mohsen Sabbaghi
 * @version 2022.1.1
 * https://www.linkedin.com/in/sabbaghi/
 * @since 4/3/2022
 */

@FeignClient(name = "rahyab", url = "${rahyab.url}", path = "${rahyab.path}")
public interface RahyabFeignClient {
    //commentssxczc

    @RequestMapping(
            method = RequestMethod.GET,
            value = "/ip.ashx",
            produces = "text/plain")
    String ipEnquiry();

    @RequestMapping(
            method = RequestMethod.POST,
            value = "/send",
            consumes = "application/json",
            produces = "application/x-www-form-urlencoded; charset=utf-8;"
    )
    @Headers({"accept","ut-8"})
    RahyabDto send(@RequestParam(value="username") String username,
                   @RequestParam(value="password") String password,
                   @RequestParam(value="from") String form,
                   @RequestParam(value="to") String to,
                   @RequestParam(value="farsi") boolean farsi,
                   @RequestParam(value="message") String message
    );
}
