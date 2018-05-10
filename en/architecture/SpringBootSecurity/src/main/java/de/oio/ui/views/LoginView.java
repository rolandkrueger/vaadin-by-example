package de.oio.ui.views;

import com.google.common.eventbus.EventBus;
import com.vaadin.event.ShortcutAction;
import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import de.oio.ui.MainUI;
import de.oio.spring.security.UserAuthenticationService;
import de.oio.ui.components.GoToMainViewLink;
import de.oio.ui.events.NavigationEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

@SpringView(name = LoginView.NAME)
public class LoginView extends AbstractView implements Button.ClickListener {

    public final static String NAME = "login";

    @Autowired
    private UserAuthenticationService userAuthenticationService;

    private String forwardTo;
    private TextField nameTF;
    private PasswordField passwordTF;

    public LoginView() {
        addComponent(new Label(
                "Please enter your credentials:"));
        nameTF = new TextField();
        nameTF.setRequiredIndicatorVisible(true);
        nameTF.focus();

        passwordTF = new PasswordField();
        passwordTF.setRequiredIndicatorVisible(true);

        addComponent(nameTF);
        addComponent(passwordTF);

        Button loginButton = new Button("Login");
        loginButton.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        loginButton.addClickListener(this);
        loginButton.setIcon(VaadinIcons.SIGN_IN);
        addComponent(loginButton);

        addComponent(new GoToMainViewLink());
    }

    @Override
    public void enter(ViewChangeEvent event) {
        forwardTo = event.getParameters();
    }

    @Override
    public void buttonClick(ClickEvent event) {
        if (!nameTF.isEmpty() && !passwordTF.isEmpty()) {
            Authentication authentication = new UsernamePasswordAuthenticationToken(nameTF.getValue(), passwordTF.getValue());
            if (userAuthenticationService.loginUser(authentication)) {
                EventBus eventbus = MainUI.getCurrent().getEventbus();
                eventbus.post(new NavigationEvent(this, forwardTo));
            } else {
                passwordTF.setValue("");
            }
        } else {
            if (nameTF.isEmpty()) {
                Notification.show("Please enter your username");
            }
            if (passwordTF.isEmpty()) {
                Notification.show("Please enter your password");
            }
        }
    }

    public static String loginPathForRequestedView(String requestedViewName) {
        return NAME + "/" + requestedViewName;
    }
}
