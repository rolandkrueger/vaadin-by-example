package org.vaadin.grundlagenbuch.properties;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.*;
import com.vaadin.ui.*;

import javax.servlet.annotation.WebServlet;

/**
 * Beispielanwendung, die die Standardeigenschaften von Feldkomponenten zeigt.
 *
 * @author Roland Krüger
 */
@Theme("valo")
public class FieldPropertiesUI extends UI {

    // Annotationsbasierte Konfiguration des Vaadin Servlets mit Servlet 3.0
    @WebServlet(value = "/*")
    @VaadinServletConfiguration(productionMode = false, ui = FieldPropertiesUI.class)
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest vaadinRequest) {
        VerticalLayout mainLayout = new VerticalLayout();
        mainLayout.setSpacing(true);
        mainLayout.setMargin(true);

        Label heading = new Label("Standardeigenschaften von Feldomponenten");
        heading.setStyleName("h1");

        // Textfeld, für das eine Eingabe erforderlich ist
        TextField textField = new TextField("Ihre Eingabe:");
        textField.setRequired(true);
        textField.setRequiredError("Ihre Eingabe ist erforderlich!");

        // Layout für zwei Schaltflächen 'Ok' und 'Zurücksetzen'
        HorizontalLayout buttonLayout = new HorizontalLayout();
        buttonLayout.setSpacing(true);
        Button okButton = new Button("Ok");
        okButton.addClickListener(e -> {
            // Wenn die Eingabe in das Textfeld gültig ist, soll diese als
            // Notification angezeigt werden
            if (textField.isValid()) {
                Notification.show("Ihre Eingabe war: " + textField.getValue());
            }
        });
        Button clearButton = new Button("Zurücksetzen");
        // Textfeld leeren, wenn man auf 'Zurücksetzen' klickt
        clearButton.addClickListener(e -> textField.clear());
        buttonLayout.addComponents(okButton, clearButton);

        mainLayout.addComponents(heading, textField, buttonLayout);
        setContent(mainLayout);
    }
}
