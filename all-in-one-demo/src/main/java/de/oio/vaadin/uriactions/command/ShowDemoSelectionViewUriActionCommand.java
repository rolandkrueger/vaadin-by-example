package de.oio.vaadin.uriactions.command;

import de.oio.vaadin.DemoUI;
import org.roklib.urifragmentrouting.UriActionCommand;

public class ShowDemoSelectionViewUriActionCommand extends AbstractUriActionCommand {
  @Override
  public void run() {
    DemoUI.getCurrent().getViewManager().showDemoSelectionView();
  }
}
