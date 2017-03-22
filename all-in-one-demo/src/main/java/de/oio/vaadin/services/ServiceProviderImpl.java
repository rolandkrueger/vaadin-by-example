package de.oio.vaadin.services;

import com.vaadin.ui.UI;
import de.oio.vaadin.event.EventBus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.vaadin.spring.annotation.UIScope;

@Component
@UIScope
public class ServiceProviderImpl implements ServiceProvider {
  private final EventBus eventBus;

  @Autowired
  public ServiceProviderImpl(EventBus eventBus) {
    this.eventBus = eventBus;
  }

  @Override
  public EventBus getEventbus() {
    return eventBus;
  }

}
