package ir.bki.notificationservice.controller;

import ir.bki.notificationservice.service.SendNotificationService;
import org.keycloak.KeycloakPrincipal;
import org.keycloak.KeycloakSecurityContext;
import org.keycloak.adapters.springsecurity.token.KeycloakAuthenticationToken;
import org.keycloak.representations.IDToken;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;
import java.security.Principal;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 3/26/2022
 */
@RestController
@RequestMapping("sms1/mobiles/{mobile-no}")
public class SendSmsNotificationController {

    private final SendNotificationService sendSmsNotificationService;

    public SendSmsNotificationController(SendNotificationService sendSmsNotificationService) {
        this.sendSmsNotificationService = sendSmsNotificationService;
    }

    @GetMapping
    @RolesAllowed({"USER"})
    public ResponseEntity<String> sayHello() {
        return ResponseEntity.ok("Hello World " + System.currentTimeMillis());
    }

    @GetMapping("/send")
    @RolesAllowed({"ADMIN", "USER"})
    public ResponseEntity<String> send(@PathVariable("mobile-no") String mobileNo) {//, @RequestBody String messageBody
//        sendSmsNotificationService.send(mobileNo, messageBody);
        return ResponseEntity.ok("DONE");
    }

    @RolesAllowed("ADMIN")
    @GetMapping(value = "/balance")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
    public String getBlanace() {
        KeycloakAuthenticationToken authentication = (KeycloakAuthenticationToken) SecurityContextHolder.getContext()
                .getAuthentication();

        final Principal principal = (Principal) authentication.getPrincipal();

        System.out.println("##principal: " + principal.getName() + ";principal: " + principal);

        if (principal instanceof KeycloakPrincipal) {

            KeycloakPrincipal<KeycloakSecurityContext> kPrincipal = (KeycloakPrincipal<KeycloakSecurityContext>) principal;
            IDToken token = kPrincipal.getKeycloakSecurityContext().getIdToken();
//
//            Map<String, Object> customClaims = token.getOtherClaims();

            System.out.println("#token: " + token);
            System.out.println("#Realm: " + kPrincipal.getKeycloakSecurityContext().getRealm());
            System.out.println("#Name: " + kPrincipal.getKeycloakSecurityContext().getToken().getName());
            System.out.println("#Email: " + kPrincipal.getKeycloakSecurityContext().getToken().getEmail());
            System.out.println("#Subject: " + kPrincipal.getKeycloakSecurityContext().getToken().getSubject());
            System.out.println("#PreferredUsername: " + kPrincipal.getKeycloakSecurityContext().getToken().getPreferredUsername());
            System.out.println("#RealmAccess: " + kPrincipal.getKeycloakSecurityContext().getToken().getRealmAccess().getRoles());

        }
        return sendSmsNotificationService.getBalance("SMS CENTER NUMBER") + "";
    }
}
