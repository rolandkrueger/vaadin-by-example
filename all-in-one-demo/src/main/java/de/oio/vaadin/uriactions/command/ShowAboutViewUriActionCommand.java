package de.oio.vaadin.uriactions.command;

import de.oio.vaadin.DemoUI;

public class ShowAboutViewUriActionCommand extends AbstractUriActionCommand {
  @Override
  public void run() {
    DemoUI.getCurrent().getViewManager().showAboutView();
  }
}
