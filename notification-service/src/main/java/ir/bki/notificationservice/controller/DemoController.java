package ir.bki.notificationservice.controller;

import io.micrometer.core.annotation.Timed;
import ir.bki.notificationservice.service.DemoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 4/4/2022
 */
//https://www.appsdeveloperblog.com/enforcing-resilience-in-a-spring-boot-app-using-resilience4j/
@RestController
@RequestMapping("/resilience")
public class DemoController {
    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping("/cb")
    @Timed
    public String circuitBreaker() {
        return demoService.circuitBreaker();
    }
    @Timed
    @GetMapping("/bulkhead")
    public String bulkhead() {
        return demoService.bulkHead();
    }
    @Timed
    @GetMapping("/tl")
    public CompletableFuture<String> timeLimiter() {
        return demoService.timeLimiter();
    }
    @Timed
    @GetMapping("/rl")
    public String rateLimiter() {
        return demoService.rateLimiter();
    }
    @Timed
    @GetMapping("/retry")
    public String retry() {
        return demoService.retry();
    }
}
