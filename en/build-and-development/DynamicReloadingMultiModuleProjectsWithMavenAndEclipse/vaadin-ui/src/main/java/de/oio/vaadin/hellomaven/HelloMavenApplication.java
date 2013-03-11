package de.oio.vaadin.hellomaven;

import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

import de.oio.vaadin.api.GreetingService;

public class HelloMavenApplication extends UI {
  @Override
  public void init(VaadinRequest request) {

    // use a service that comes from the backend API sub-module
    GreetingService service = new GreetingService();

    Label label = new Label("<h1>Hello Maven Multi-Module</h1><br/>Greeting from the API:<br/>" + service.greet(),
        ContentMode.HTML);
    VerticalLayout layout = new VerticalLayout();
    layout.addComponent(label);
    setContent(layout);
  }
}
