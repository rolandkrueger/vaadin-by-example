package de.oio.ui;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.server.*;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.UI;
import de.oio.model.User;
import de.oio.service.VaadinUIService;
import de.oio.service.impl.VaadinUIServiceImpl;
import de.oio.ui.events.LogoutEvent;
import de.oio.ui.events.NavigationEvent;
import de.oio.ui.security.SecurityErrorHandler;
import de.oio.ui.security.ViewAccessDecisionManager;
import de.oio.ui.views.ErrorView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@SpringUI(path = "")
@Theme("valo")
@PreserveOnRefresh
public class MainUI extends UI {

    private static final Logger LOG = LoggerFactory.getLogger(MainUI.class);
    private static final VaadinUIService uiService = new VaadinUIServiceImpl();    @Autowired
    private SpringViewProvider viewProvider;

    @Autowired
    private ViewAccessDecisionManager viewAccessDecisionManager;

    private EventBus eventbus;

    public static MainUI getCurrent() {
        return (MainUI) UI.getCurrent();
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        buildNavigator();
        VaadinSession.getCurrent().setErrorHandler(new SecurityErrorHandler(eventbus, getNavigator()));

        viewAccessDecisionManager.checkAccessRestrictionForRequestedView(getNavigator());

        Page.getCurrent().setTitle("Vaadin and Spring Security Demo");
    }

    private void buildNavigator() {
        Navigator navigator = new Navigator(this, this);
        navigator.addProvider(viewProvider);
        navigator.setErrorView(ErrorView.class);
        setNavigator(navigator);
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
