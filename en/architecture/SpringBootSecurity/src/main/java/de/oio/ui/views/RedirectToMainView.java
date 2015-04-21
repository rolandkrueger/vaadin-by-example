package de.oio.ui.views;

import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import de.oio.ui.MainUI;

@SpringView(name = "")
public class RedirectToMainView extends Navigator.EmptyView {

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent viewChangeEvent) {
        MainUI.getCurrent().getNavigator().navigateTo(MainView.NAME);
    }
}
