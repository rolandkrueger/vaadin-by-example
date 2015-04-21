package de.oio.service.impl;

import com.vaadin.ui.Notification;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Service;

@Service
public class AdminService {

    @Secured("ROLE_ADMIN")
    public void doSomeAdministrationTask() {
        Notification.show("Restricted admin task performed", "", Notification.Type.HUMANIZED_MESSAGE);
    }
}
