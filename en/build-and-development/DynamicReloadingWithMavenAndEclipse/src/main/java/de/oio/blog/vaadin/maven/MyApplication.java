package de.oio.blog.vaadin.maven;

import com.vaadin.terminal.WrappedRequest;
import com.vaadin.ui.Label;
import com.vaadin.ui.Root;

public class MyApplication extends Root {
  @Override
  public void init(WrappedRequest request) {
    Label label = new Label(
        "Hello Vaadin user! Change this text while running 'mvn jetty:run-exploded' and recompile with 'mvn compile -Pdevelopment'.");
    addComponent(label);
  }

}
