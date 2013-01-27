package de.oio.vaadin.client.widgets;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface IconResources extends ClientBundle {
  public static final IconResources INSTANCE = GWT.create(IconResources.class);

  @Source("reset.png")
  ImageResource resetIcon();
}
