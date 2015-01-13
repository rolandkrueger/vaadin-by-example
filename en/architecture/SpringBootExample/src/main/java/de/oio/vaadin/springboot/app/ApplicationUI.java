package de.oio.vaadin.springboot.app;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import org.vaadin.spring.VaadinUI;

@VaadinUI
@Theme("valo")
public class ApplicationUI extends UI {

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        setContent(new Label("Hello world! " + getUIId()));
    }
}
