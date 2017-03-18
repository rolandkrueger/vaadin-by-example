package de.oio.vaadin.services;

import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;
import de.oio.vaadin.event.EventBus;

import java.io.Serializable;

public interface ServiceProvider extends Serializable {
  EventBus getEventbus();
}
