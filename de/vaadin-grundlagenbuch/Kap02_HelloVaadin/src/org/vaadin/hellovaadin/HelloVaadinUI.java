package org.vaadin.hellovaadin;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Theme("hellovaadin")
public class HelloVaadinUI extends UI {

  @Override
  protected void init(VaadinRequest request) {
    final VerticalLayout layout = new VerticalLayout();
    layout.setMargin(true);
    layout.setSpacing(true);

    buildHomeScreen(layout);
    setContent(layout);
  }

  private void buildHomeScreen(final ComponentContainer layout) {
    final TextField nameTextField = new TextField("Wie lautet Ihr Name?");
    nameTextField.setRequired(true);
    final Button sayHelloButton = new Button("Sag mal Hallo...");

    sayHelloButton.addClickListener(new Button.ClickListener() {
      @Override
      public void buttonClick(ClickEvent event) {
        if (nameTextField.isValid()) {
          layout.removeAllComponents();
          buildHelloScreen(layout, nameTextField.getValue());
        } else {
          Notification.show("Geben Sie bitte Ihren Namen ein.");
        }
      }
    });

    layout.addComponent(nameTextField);
    layout.addComponent(sayHelloButton);
  }

  private void buildHelloScreen(final ComponentContainer layout, String name) {
    final Label helloLabel = new Label(String.format("Hallo %s!", name));
    final Button backButton = new Button("<< Zurück");

    backButton.addClickListener(new Button.ClickListener() {
      @Override
      public void buttonClick(ClickEvent event) {
        layout.removeAllComponents();
        buildHomeScreen(layout);
      }
    });

    layout.addComponent(helloLabel);
    layout.addComponent(backButton);
  }
}