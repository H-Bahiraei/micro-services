package ir.bki.notificationservice.controller;

import ir.bki.notificationservice.dto.MagfaDto;
import ir.bki.notificationservice.service.client.MagfaFeignClient;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.GET;
import java.util.List;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/3/2022
 */
@RestController
@RequestMapping("magfa")
public class MagfaSmsController {

    private MagfaFeignClient magfaFeignClient;

    public MagfaSmsController(MagfaFeignClient magfaFeignClient) {
        this.magfaFeignClient = magfaFeignClient;
    }

    @GetMapping
    public MagfaDto getBalance(){
        return magfaFeignClient.getBalance();
    }

    @GetMapping("send")
    public MagfaDto send(){
        MagfaDto magfaDto=new MagfaDto();
        magfaDto.setRecipients(List.of("989124402951"));
        magfaDto.setSenders(List.of("9830007682"));
        magfaDto.setMessages(List.of("سلام بچه ها"));
        System.err.println("#magfaDto: "+magfaDto);
        MagfaDto magfaDto1= magfaFeignClient.send(magfaDto);
        System.out.println("#magfaDto2: "+magfaDto1);
        return new MagfaDto();
    }

}
