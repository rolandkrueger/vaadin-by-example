package de.oio.vaadin.uriactions;

import de.oio.vaadin.DemoUI;
import org.roklib.urifragmentrouting.UriActionCommand;

public class ShowDemoUriActionCommand implements UriActionCommand {
  private String demo;

  public ShowDemoUriActionCommand(String demo) {
    this.demo = demo;
  }

  @Override
  public void run() {
    DemoUI.getCurrent().getViewManager().showDemoView(demo);
  }
}
