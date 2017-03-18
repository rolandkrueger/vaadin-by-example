package de.oio.vaadin.event;

import java.io.Serializable;

public interface EventBus extends Serializable {
  void post(Event event);

  void register(Object listener);

  void unregister(Object listener);
}