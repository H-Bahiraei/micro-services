package ir.bki.otpservice.controller;

import ir.bki.otpservice.OtpServiceApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author H-Bahiraei
 * Created on 6/27/2022
 */

/**
 * Base composite annotation for integration tests.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@SpringBootTest(classes = OtpServiceApplication.class)
@AutoConfigureMockMvc
public @interface IntegrationTest {
}
