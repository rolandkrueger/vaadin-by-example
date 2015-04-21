package de.oio.service.impl;

import com.google.common.eventbus.EventBus;
import de.oio.ui.MainUI;
import de.oio.ui.events.NavigationEvent;
import de.oio.ui.views.AccessDeniedView;
import de.oio.ui.views.LoginView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class VaadinAccessDecisionManager implements AccessDecisionManager {

    private Logger LOG = LoggerFactory.getLogger(VaadinAccessDecisionManager.class);

    private AccessDecisionManager delegate;

    public VaadinAccessDecisionManager() {
    }

    public void setAccessDecisionManager(AccessDecisionManager accessDecisionManager) {
        this.delegate = accessDecisionManager;
    }

    @Override
    public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes) throws AccessDeniedException {
        try {
            if (configAttributes == null) {
                // no action if an object with no access restrictions is visited
                return;
            }
            delegate.decide(authentication, object, configAttributes);
        } catch (AccessDeniedException adExc) {
            final EventBus eventbus = MainUI.getCurrent().getEventbus();
            if (MainUI.getCurrent().isUserAnonymous()) {
                eventbus.post(new NavigationEvent(this, LoginView.loginPathForRequestedView(MainUI.getCurrent().getNavigator().getState())));
                throw adExc;
            } else {
                eventbus.post(new NavigationEvent(this, AccessDeniedView.NAME));
                throw adExc;
            }
        } catch (Exception exc) {
            LOG.info("Exception after authentication decision: {}", exc.getClass().getName());
            exc.printStackTrace();
        }
    }

    @Override
    public boolean supports(ConfigAttribute attribute) {
        return delegate.supports(attribute);
    }

    @Override
    public boolean supports(Class<?> clazz) {
        return delegate.supports(clazz);
    }
}
