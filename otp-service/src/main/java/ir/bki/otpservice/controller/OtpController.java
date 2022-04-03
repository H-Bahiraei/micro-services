package ir.bki.otpservice.controller;

import ir.bki.otpservice.model.OtpEntity;
import ir.bki.otpservice.service.OtpServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 3/14/2022
 */
@Controller
@RequestMapping("otp")
public class OtpController {

    private final OtpServiceImpl otpService;

    public OtpController(OtpServiceImpl otpService) {
        this.otpService = otpService;
    }
    //-------------- Business Method -------------------
    @GetMapping
    public ResponseEntity<List<OtpEntity>> findAll() {
        List<OtpEntity> list = otpService.findAll();
        return ResponseEntity.ok(list);
    }
    @GetMapping("/{id}")
    public ResponseEntity<OtpEntity> findById(@PathVariable("id") long id) {
        OtpEntity otpEntity=otpService.findById(id);
        return ResponseEntity.ok(otpEntity);
    }

}
