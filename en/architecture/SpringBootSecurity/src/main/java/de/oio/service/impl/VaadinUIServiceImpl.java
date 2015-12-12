package de.oio.service.impl;

import com.vaadin.ui.UI;
import de.oio.service.VaadinUIService;
import de.oio.ui.MainUI;
import de.oio.ui.events.NavigationEvent;
import de.oio.ui.views.AccessDeniedView;

public class VaadinUIServiceImpl implements VaadinUIService {

    @Override
    public void postNavigationEvent(Object source, String target) {
        MainUI.getCurrent().getEventbus().post(new NavigationEvent(source, target));
    }

    @Override
    public boolean isUserAnonymous() {
        return MainUI.getCurrent().isUserAnonymous();
    }
}
