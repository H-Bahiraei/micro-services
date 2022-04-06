package ir.bki.notificationservice.service.client;

import ir.bki.notificationservice.config.feing.FeignClientConfiguration;
import ir.bki.notificationservice.dto.MagfaDto;
import ir.bki.notificationservice.dto.MagfaRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/3/2022
 */
//https://docs.spring.io/spring-cloud-openfeign/docs/current/reference/html/
@FeignClient(name = "magfa", url = "${magfa.url}", path = "${magfa.path}")
public interface MagfaFeignClient {

    @RequestMapping(
            method= RequestMethod.GET,
            value="/balance",
            consumes="application/json")
    MagfaDto getBalance();

    @RequestMapping(
            method= RequestMethod.POST,
            value="/send"
            , consumes="application/json",
            produces="application/json"
    )
    MagfaDto send(@RequestBody MagfaRequestDto body);
}
