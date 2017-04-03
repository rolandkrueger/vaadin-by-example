package de.oio.vaadin.views.impl;

import com.vaadin.ui.Label;
import de.oio.vaadin.components.TranslatedCustomLayout;
import de.oio.vaadin.services.templating.TemplateData;
import de.oio.vaadin.views.View;

public class ErrorView extends TranslatedCustomLayout {
  private final String currentUriFragment;

  public ErrorView(String currentUriFragment, TemplateData templateData) {
    super(templateData);
    this.currentUriFragment = currentUriFragment;
  }

  @Override
  public View buildLayout() {
    super.buildLayout();
    getLayout().addComponent(new Label(currentUriFragment), "urifragment");
    return this;
  }
}
