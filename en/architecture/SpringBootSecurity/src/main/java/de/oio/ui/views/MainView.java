package de.oio.ui.views;

import com.google.common.eventbus.Subscribe;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import de.oio.model.User;
import de.oio.service.AdminService;
import de.oio.ui.MainUI;
import de.oio.ui.components.LogoutLink;
import de.oio.ui.events.LogoutEvent;
import de.oio.ui.events.UserLoggedInEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;

@SpringView(name = MainView.NAME)
public class MainView extends AbstractView {

    @Autowired
    private AdminService adminService;

    public final static String NAME = "main";

    private String welcomeLabelText;
    private LogoutLink logoutLink;

    public MainView() {
        welcomeLabelText = "";

        updateWelcomeMessage();
        Label welcomeLabel = new Label(welcomeLabelText, ContentMode.HTML);

        addComponent(welcomeLabel);

        HorizontalLayout horizontalLayout = new HorizontalLayout();
        horizontalLayout.setSpacing(true);
        final Link profileLink = new Link("Your Profile", new ExternalResource("#!" + ProfileView.NAME));
        profileLink.setIcon(VaadinIcons.USER);
        horizontalLayout.addComponent(profileLink);
        final Link invalidLink = new Link("Go to some invalid page", new ExternalResource("#!invalid_page"));
        invalidLink.setIcon(VaadinIcons.BOMB);
        horizontalLayout.addComponent(invalidLink);

        logoutLink = new LogoutLink();
        logoutLink.updateVisibility();
        horizontalLayout.addComponent(logoutLink);

        Link adminLink = new Link("Admin page", new ExternalResource("#!" + AdminView.NAME));
        adminLink.setIcon(VaadinIcons.LOCK);
        horizontalLayout.addComponent(adminLink);

        Link aboutLink = new Link("About", new ExternalResource("#!" + AboutView.NAME));
        aboutLink.setIcon(VaadinIcons.QUESTION_CIRCLE);
        horizontalLayout.addComponent(aboutLink);
        addComponent(horizontalLayout);

        Button adminButton = new Button("Admin Button");
        adminButton.addClickListener(new Button.ClickListener() {
            @Override
            public void buttonClick(Button.ClickEvent event) {
                adminService.doSomeAdministrationTask();
            }
        });
        addComponent(adminButton);

        registerWithEventbus();
    }

    private void updateWelcomeMessage() {
        String username = null;
        if (!MainUI.getCurrent().isUserAnonymous()) {
            final User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            username = principal.getFullName();
        }

        welcomeLabelText = (username == null ? "<h1>Welcome Stranger</h1><hr/>You're currently not logged in.<hr/>"
                        : "<h1>Welcome " + username + "!</h1><hr/>");
    }

    @Subscribe
    public void userLoggedIn(UserLoggedInEvent event) {
        updateWelcomeMessage();
        logoutLink.updateVisibility();
    }

    @Subscribe
    public void userLoggedOut(LogoutEvent event) {
        updateWelcomeMessage();
        logoutLink.updateVisibility();
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
