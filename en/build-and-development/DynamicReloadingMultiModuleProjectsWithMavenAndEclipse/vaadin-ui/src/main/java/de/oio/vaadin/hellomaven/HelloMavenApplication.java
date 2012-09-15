package de.oio.vaadin.hellomaven;

import com.vaadin.terminal.WrappedRequest;
import com.vaadin.terminal.gwt.client.ui.label.ContentMode;
import com.vaadin.ui.Label;
import com.vaadin.ui.Root;

import de.oio.vaadin.api.GreetingService;

public class HelloMavenApplication extends Root {
  @Override
  public void init(WrappedRequest request) {

    // use a service that comes from the backend API sub-module
    GreetingService service = new GreetingService();

    Label label = new Label(
        "<h1>Hello Maven Multi-Module</h1><br/>Greeting from the API:<br/>"
            + service.greet(), ContentMode.XHTML);
    addComponent(label);
  }

}
