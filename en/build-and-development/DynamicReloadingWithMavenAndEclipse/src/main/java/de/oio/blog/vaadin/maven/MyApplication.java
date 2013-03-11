package de.oio.blog.vaadin.maven;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class MyApplication extends UI {
  @Override
  public void init(VaadinRequest request) {
    VerticalLayout layout = new VerticalLayout();

    Label label = new Label(
        "Hello Vaadin user! Change this text while running 'mvn jetty:run-exploded' and recompile with 'mvn compile -Pdevelopment'.");
    layout.addComponent(label);
    setContent(layout);
  }
}
