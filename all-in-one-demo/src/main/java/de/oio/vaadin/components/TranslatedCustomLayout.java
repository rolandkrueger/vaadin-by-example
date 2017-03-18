package de.oio.vaadin.components;

import com.vaadin.server.VaadinService;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomLayout;
import de.oio.vaadin.services.templating.TemplateData;
import de.oio.vaadin.views.View;
import org.vaadin.highlighter.ComponentHighlighterExtension;

import java.io.IOException;

import static com.google.common.base.Preconditions.checkNotNull;

public class TranslatedCustomLayout implements View {
  private final TemplateData templateData;
  private CustomLayout layout;

  public TranslatedCustomLayout(TemplateData templateData) {
    this.templateData = checkNotNull(templateData);
  }

  @Override
  public View buildLayout() {
    layout = createTranslatedCustomLayout(templateData);

    if (layout != null && !VaadinService.getCurrent().getDeploymentConfiguration().isProductionMode()) {
      new ComponentHighlighterExtension(getLayout()).setComponentDebugLabel(getClass().getName()
          + " (template name: " + templateData.name() + ".html)");
    }
    return this;
  }

  @Override
  public Component getContent() {
    return layout;
  }

  protected CustomLayout getLayout() {
    return layout;
  }

  private CustomLayout createTranslatedCustomLayout(TemplateData templateData) {
    CustomLayout layout = null;
    try {
      layout = new CustomLayout(templateData.data());
    } catch (IOException ioExc) {
      throw new IllegalStateException("Error while loading CustomLayout template " + templateData, ioExc);
    }
    return layout;
  }
}
