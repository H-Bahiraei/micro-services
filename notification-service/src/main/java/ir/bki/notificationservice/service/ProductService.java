package ir.bki.notificationservice.service;

import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 3/27/2022
 */
@Component
public class ProductService {

    public List<String> getProducts() {
        return Arrays.asList("iPad", "iPod", "iPhone");
    }
}