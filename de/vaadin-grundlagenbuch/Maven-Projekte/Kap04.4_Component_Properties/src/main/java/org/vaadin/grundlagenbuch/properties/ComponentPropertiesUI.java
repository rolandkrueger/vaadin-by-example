package org.vaadin.grundlagenbuch.properties;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.*;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;

/**
 * Beispielanwendung, die die verschiedenen Standardeigenschaften von Vaadin Komponenten zeigt.
 *
 * @author Roland Krüger
 */
@Theme("valo")
public class ComponentPropertiesUI extends UI {

    // Annotationsbasierte Konfiguration des Vaadin Servlets mit Servlet 3.0
    @WebServlet(value = "/*")
    @VaadinServletConfiguration(productionMode = false, ui = ComponentPropertiesUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);

        Label heading = new Label("Standardeigenschaften von Vaadin-Komponenten");
        heading.setStyleName("h1");

        Button button = new Button();
        button.setCaption("Caption einer Schaltfläche");
        button.setIcon(FontAwesome.REBEL, "Icon der Schaltfläche");
        button.setId("rebel-button");
        button.setStyleName("rebel-style");

        TextField textField = new TextField("Caption eines Textfeldes");
        textField.setIcon(FontAwesome.EMPIRE);
        textField.setDescription("Beschreibung des Textfeldes");

        TextField disabledTextField = new TextField("Deaktiviertes Textfeld");
        disabledTextField.setValue("Text ...");
        disabledTextField.setEnabled(false);

        TextField readOnlyTextField = new TextField("Read-only Textfeld");
        readOnlyTextField.setValue("Unveränderlicher Text");
        readOnlyTextField.setReadOnly(true);

        Button styledButton = new Button("Ein Button mit geändertem Primary Style");
        styledButton.setPrimaryStyleName("my-own-style");

        mainLayout.addComponents(heading, button, textField, disabledTextField, readOnlyTextField, styledButton);
        setContent(mainLayout);
    }
}
