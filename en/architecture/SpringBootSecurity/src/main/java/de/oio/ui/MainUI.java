package de.oio.ui;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;
import de.oio.model.User;
import de.oio.service.VaadinUIService;
import de.oio.service.impl.VaadinAccessDecisionManager;
import de.oio.service.impl.VaadinUIServiceImpl;
import de.oio.ui.events.LogoutEvent;
import de.oio.ui.events.NavigationEvent;
import de.oio.ui.vaadin.FixedNavigator;
import de.oio.ui.views.AccessDeniedView;
import de.oio.ui.views.ErrorView;
import de.oio.ui.views.LoginView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.annotation.SecuredAnnotationSecurityMetadataSource;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.util.SimpleMethodInvocation;
import org.springframework.util.ReflectionUtils;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import java.util.Collection;

@SpringUI(path = "")
@Theme("valo")
@PreserveOnRefresh
public class MainUI extends UI {

    private static final Logger LOG = LoggerFactory.getLogger(MainUI.class);
    private static final VaadinUIService uiService = new VaadinUIServiceImpl();

    @Autowired
    private SpringViewProvider viewProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private VaadinAccessDecisionManager accessDecisionManager;

    private EventBus eventbus;

    public static MainUI getCurrent() {
        return (MainUI) UI.getCurrent();
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VaadinSession.getCurrent().setErrorHandler(new ErrorHandler() {
            @Override
            public void error(com.vaadin.server.ErrorEvent event) {
                LOG.error("Error handler caught exception {}", event.getThrowable().getClass().getName());
                if (event.getThrowable() instanceof AccessDeniedException ||
                        event.getThrowable().getCause() instanceof AccessDeniedException) {
                    if (MainUI.getCurrent().isUserAnonymous() && !getNavigator().getState().startsWith(LoginView.NAME)) {
                        eventbus.post(new NavigationEvent(this, LoginView.loginPathForRequestedView(getNavigator().getState())));
                    } else if (!MainUI.getCurrent().isUserAnonymous()) {
                        eventbus.post(new NavigationEvent(this, AccessDeniedView.NAME));
                    }
                } else {
                    event.getThrowable().printStackTrace();
                }
            }
        });
        Navigator navigator = new FixedNavigator(this, this);
        navigator.addProvider(viewProvider);
        navigator.setErrorView(ErrorView.class);
        setNavigator(navigator);

        checkAccessRestrictionForRequestedView();

        Page.getCurrent().setTitle("Vaadin and Spring Security Demo");
    }

    private void checkAccessRestrictionForRequestedView() {
        final View targetView = viewProvider.getView(getNavigator().getState());

        if (targetView != null) {
            SecuredAnnotationSecurityMetadataSource metadataSource = new SecuredAnnotationSecurityMetadataSource();
            final Collection<ConfigAttribute> attributes = metadataSource.getAttributes(new SimpleMethodInvocation(targetView,
                    ReflectionUtils.findMethod(View.class, "enter", ViewChangeListener.ViewChangeEvent.class)));
            try {
                accessDecisionManager.decide(SecurityContextHolder.getContext().getAuthentication(), targetView, attributes);
            } catch (AccessDeniedException adExc) {
                // must be ignored as this exception is already handled in the AccessDecisionManager
            }
        }
    }

    public EventBus getEventbus() {
        return eventbus;
    }

    public User getCurrentUser() {
        if (isUserAnonymous()) {
            return null;
        } else {
            return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        }
    }

    @PostConstruct
    private void initEventbus() {
        eventbus = new EventBus("main");
        eventbus.register(this);
    }

    public boolean isUserAnonymous() {
        return SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken;
    }

    @Subscribe
    public void userLoggedOut(LogoutEvent event) throws ServletException {
        ((VaadinServletRequest) VaadinService.getCurrentRequest()).getHttpServletRequest().logout();
        VaadinSession.getCurrent().close();
        Page.getCurrent().setLocation("/");
    }

    @Subscribe
    public void handleNavigation(NavigationEvent event) {
        getNavigator().navigateTo(event.getTarget());
    }

    public static VaadinUIService getUiService() {
        return uiService;
    }
}
