package de.oio.vaadin.services.templating;

import java.io.Serializable;

public interface TemplatingService extends Serializable {
  TemplateData getLayoutTemplate(String templatePath);
}