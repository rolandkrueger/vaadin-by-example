package de.oio.vaadin.services;

import de.oio.vaadin.event.EventBus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.Serializable;

@Slf4j
public abstract class AbstractUsesServiceProvider implements Serializable {
  private ServiceProvider serviceProvider;

  @Autowired
  public void setUIServiceProvider(ServiceProvider serviceProvider) {
    if (log.isDebugEnabled()) {
      log.debug("Setting UI service provider for " + getClass().getName());
    }
    this.serviceProvider = serviceProvider;
    onServiceProviderSet();
  }

  protected void onServiceProviderSet() {
    // to be overwritten by subclasses as required
  }

  protected EventBus eventbus() {
    if (serviceProvider == null) {
      throw new IllegalStateException("Service Provider has not been set for  "
          + getClass().getName());
    }
    return serviceProvider.getEventbus();
  }
}
