package de.oio.spring.security;

import com.google.common.eventbus.EventBus;
import com.vaadin.ui.UI;
import de.oio.service.VaadinUIService;
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
import org.springframework.security.authentication.InsufficientAuthenticationException;

@Component
public class VaadinAccessDecisionManager implements AccessDecisionManager {

    private Logger LOG = LoggerFactory.getLogger(VaadinAccessDecisionManager.class);

    private AccessDecisionManager delegate;
    public static final VaadinUIService UI_SERVICE = MainUI.getUiService();

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
            
            // delegate access decision itself to super class
            delegate.decide(authentication, object, configAttributes);
            
        } catch (AccessDeniedException adExc) {
            
            // we handle security exceptions in the Vaadin way, i. e. we publish appropriate events on the event bus instead
            // of redirecting to some error page (remember that we've a single-page application)
            if (UI_SERVICE.isUserAnonymous()) {
                
                UI_SERVICE.postNavigationEvent(this, 
                        LoginView.loginPathForRequestedView(UI.getCurrent()
                                .getNavigator().getState()));
                throw adExc;
                
            } else {
                
                // if she is logged in but doesn't have adequate access rights, send her to the access denied view
                UI_SERVICE.postNavigationEvent(this, AccessDeniedView.NAME);
                throw adExc;
                
            }
            
        } catch (InsufficientAuthenticationException exc) {
            LOG.info("Exception after authentication decision: {}", 
                    exc.getClass().getName());
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
