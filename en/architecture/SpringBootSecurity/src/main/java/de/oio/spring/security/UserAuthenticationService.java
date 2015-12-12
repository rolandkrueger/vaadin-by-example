package de.oio.spring.security;

import com.vaadin.ui.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    public boolean loginUser(Authentication authenticationRequest) {
        try {
            final Authentication authentication = authenticationManager.authenticate(authenticationRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return true;
        } catch (AuthenticationException aExc) {
            Notification.show("Authentication error", "Could not authenticate", Notification.Type.ERROR_MESSAGE);
            return false;
        }
    }
}
