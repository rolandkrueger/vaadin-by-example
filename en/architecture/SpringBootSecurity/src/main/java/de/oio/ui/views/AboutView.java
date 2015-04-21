package de.oio.ui.views;

import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Label;

@SpringView(name = AboutView.NAME)
public class AboutView extends AbstractView {

    public static final String NAME = "about";

    public AboutView() {
        addComponent(new Label("<h1>About</h1>", ContentMode.HTML));
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }
}
