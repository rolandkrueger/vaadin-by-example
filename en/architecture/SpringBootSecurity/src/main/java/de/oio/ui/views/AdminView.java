package de.oio.ui.views;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;
import de.oio.ui.components.GoToMainViewLink;
import org.springframework.security.access.annotation.Secured;

@Secured("ROLE_ADMIN")
@SpringView(name = AdminView.NAME)
public class AdminView extends AbstractView {

    public static final String NAME = "admin";

    public AdminView() {
        addComponent(new Label("<h1>Admin Area</h1>", ContentMode.HTML));
        addComponent(new GoToMainViewLink());
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
    }
}
