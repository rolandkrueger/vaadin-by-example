package de.oio.ui.views;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import de.oio.ui.MainUI;
import de.oio.ui.events.LogoutEvent;

@SpringView(name = LogoutView.NAME)
public class LogoutView extends Navigator.EmptyView {

    public static final String NAME = "logout";

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        MainUI.getCurrent().getEventbus().post(new LogoutEvent(this));
    }
}
