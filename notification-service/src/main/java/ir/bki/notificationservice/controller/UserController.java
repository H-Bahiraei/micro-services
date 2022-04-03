package ir.bki.notificationservice.controller;

import org.jboss.resteasy.client.jaxrs.ResteasyClientBuilder;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RealmRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.Collections;

/**
 * @author Mahdi Sharifi
 * @version 2022.1.1
 * https://www.linkedin.com/in/mahdisharifi/
 * @since 3/27/2022
 */
//https://www.springcloud.io/post/2022-02/keycloak-manager-api/#gsc.tab=0
//https://stackoverflow.com/questions/51521958/keycloak-create-realms-users-groups-programmatically
    //https://lists.jboss.org/pipermail/keycloak-user/2017-June/010944.html
    //https://www.n-k.de/2016/08/keycloak-admin-client.html
    //http://127.0.0.1:8090/auth/admin/realms/otp-realm/users/count
    //http://www.keycloak.org/docs/latest/authorization_services/index.html
@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public String createUser(){
        System.out.println("###UserController##########");
        // User "idm-admin" needs at least "manage-users, view-clients, view-realm, view-users" roles for "realm-management"
        Keycloak kc = KeycloakBuilder.builder()
                .serverUrl("http://127.0.0.1:8090/auth")
                .realm("otp-realm")
                .grantType(OAuth2Constants.PASSWORD) //
                .username("mahdi")
                .password("admin90*")
                .clientId("admin-cli")
                .resteasyClient(new ResteasyClientBuilder().connectionPoolSize(10).build())
                .build();



//        // Define user
//        UserRepresentation user = new UserRepresentation();
//        user.setEnabled(true);
//        user.setUsername("tester1");
//        user.setFirstName("First");
//        user.setLastName("Last");
//        user.setEmail("tom+tester1@tdlabs.local");
//        user.setAttributes(Collections.singletonMap("origin", Arrays.asList("demo")));
//
//        // Get realm
//        RealmResource realmResource = kc.realm("Otp-realm");
//        UsersResource usersRessource = realmResource.users();
//
//        // Create user (requires manage-users role)
//        Response response = usersRessource.create(user);
//        System.out.println("#response: "+response);
//        System.out.printf("Repsonse: %s %s%n", response.getStatus(), response.getStatusInfo());
//        System.out.println(response.getLocation());
//        String userId = CreatedResponseUtil.getCreatedId(response);

//        Create a new realm in Keycloak
        RealmRepresentation realm = new RealmRepresentation();
        realm.setRealm("demo");
        kc.realms().create(realm);
        System.out.println("Response resultRealm = AFTER");

//        CredentialRepresentation credential = new CredentialRepresentation();
//        credential.setType(CredentialRepresentation.PASSWORD);
//        credential.setValue("test123");
//
//        UserRepresentation user = new UserRepresentation();
//        user.setUsername("testuser2");
//        user.setFirstName("Test2");
//        user.setLastName("User2");
//        user.setEmail("aaa@bbb.com");
//        user.setCredentials(Arrays.asList(credential));
//        user.setEnabled(true);
//        user.setRealmRoles(Arrays.asList("ADMIN"));
//
//        // Create testuser
//        Response result = kc.realm("Otp-realm").users().create(user);
//        if (result.getStatus() != 201) {
//            System.err.println("##Couldn't create user.");
//            System.exit(0);
//        }else{
//            System.out.println("##Testuser created.... verify in keycloak!");
//        }
        return "SUCCESS";
    }
}
