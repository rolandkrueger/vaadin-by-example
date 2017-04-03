package de.oio.vaadin.uriactions.command;

import de.oio.vaadin.DemoUI;
import org.roklib.urifragmentrouting.UriActionCommand;
import org.roklib.urifragmentrouting.annotation.CurrentUriFragment;

public class ShowErrorActionCommand implements UriActionCommand {

  private String currentUriFragment;

  @Override
  public void run() {
    DemoUI.getCurrent().getViewManager().showErrorView(currentUriFragment);
  }

  @CurrentUriFragment
  public void setCurrentUriFragment(String currentUriFragment) {
    this.currentUriFragment = currentUriFragment;
  }
}
