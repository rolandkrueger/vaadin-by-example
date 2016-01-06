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

        // Setzen der Caption über den Konstruktor
        Label heading = new Label("Standardeigenschaften von Vaadin-Komponenten");
        heading.setStyleName("h1");

        Button button = new Button();
        // Setzen der Caption über explizite Setter-Methode
        button.setCaption("Caption einer Schaltfläche");
        // Setzen eines Icons
        button.setIcon(FontAwesome.REBEL);
        // Setzen der Id
        button.setId("rebel-button");
        // Setzen des Style Names
        button.setStyleName("rebel-style");

        TextField textField = new TextField("Caption eines Textfeldes");
        textField.setIcon(FontAwesome.EMPIRE);
        // Setzen der Beschreibung (wird als Tooltip angezeigt)
        textField.setDescription("Beschreibung des Textfeldes");

        TextField disabledTextField = new TextField("Deaktiviertes Textfeld");
        disabledTextField.setValue("Text ...");
        // Deaktivieren des Textfeldes
        disabledTextField.setEnabled(false);

        Label invisibleTextFieldLabel = new Label("Hier folgt ein unsichtbares Textfeld:");
        TextField invisibleTextField = new TextField("Unsichtbar");
        invisibleTextField.setVisible(false);

        TextField readOnlyTextField = new TextField("Read-only Textfeld");
        readOnlyTextField.setValue("Unveränderlicher Text");
        // Setzen des Textfeldes als Read Only
        readOnlyTextField.setReadOnly(true);

        Button styledButton = new Button("Ein Button mit geändertem Primary Style");
        // Setzen des Primary Styles
        styledButton.setPrimaryStyleName("my-own-style");

        DateField dateField = new DateField();
        // Setzen einer Beschreibung mit HTML-Elementen
        dateField.setDescription("<i>DateField</i> mit einer <ul>" +
                "<li>mehrere</li>" +
                "<li>Punkte</li>" +
                "<li>umfassenden</li>" +
                "</ul>Beschreibung");

        mainLayout.addComponents(heading, button, textField, disabledTextField, invisibleTextFieldLabel, invisibleTextField, readOnlyTextField, styledButton, dateField);
        setContent(mainLayout);
    }
}
