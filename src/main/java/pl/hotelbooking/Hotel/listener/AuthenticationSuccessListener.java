package pl.hotelbooking.Hotel.listener;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;
import pl.hotelbooking.Hotel.domain.MyUserPrincipal;
import pl.hotelbooking.Hotel.services.LoginAttemptService;

@Component
@AllArgsConstructor
public class AuthenticationSuccessListener {
    private final LoginAttemptService loginAttemptService;

    @EventListener
    public void onAuthenticationSuccess(AuthenticationSuccessEvent event) {
        Object principal = event.getAuthentication().getPrincipal();

        if (principal instanceof MyUserPrincipal) {
            MyUserPrincipal myUserPrincipal = (MyUserPrincipal) event.getAuthentication().getPrincipal();
            loginAttemptService.evictUserFromLoginAttemptCache(myUserPrincipal.getUsername());
        }

    }
}
