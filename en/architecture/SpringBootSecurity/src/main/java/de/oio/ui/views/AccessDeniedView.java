package de.oio.ui.views;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ExternalResource;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;

@SpringView(name = AccessDeniedView.NAME)
public class AccessDeniedView extends AbstractView  {

    public static final String NAME = "accessDenied";

    public AccessDeniedView() {
        addComponent(new Label("<h1>Access Denied!</h1>", ContentMode.HTML));
        addComponent(new Label("You don't have required permission to access this resource."));
        Link homeLink = new Link("Home", new ExternalResource("#"));
        homeLink.setIcon(VaadinIcons.HOME);
        addComponent(homeLink);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }

}
