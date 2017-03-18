package de.oio.vaadin.services;

import de.oio.vaadin.event.EventBus;
import de.oio.vaadin.services.templating.TemplatingService;
import de.oio.vaadin.session.SessionContext;
import de.oio.vaadin.uriactions.URIActionManager;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.vaadin.spring.UIScope;

import java.io.Serializable;

@UIScope
@Component
public class VaadinUIServices implements Serializable {

  private static final long serialVersionUID = -6765728153503813392L;

  @Getter
  @Setter
  private Object data;
  @Getter
  private final EventBus eventbus;
  @Getter
  private final URIActionManager uriActionManager;
  @Getter
  private final SessionContext context;
  @Getter
  private final TemplatingService templatingService;
  @Getter
  private final MessageProvider messageProvider;

  @Autowired
  public VaadinUIServices(final EventBus eventbus, final URIActionManager uriActionManager, final SessionContext context, final TemplatingService templatingService, final MessageProvider messageProvider) {
    this.eventbus = eventbus;
    this.uriActionManager = uriActionManager;
    this.context = context;
    this.templatingService = templatingService;
    this.messageProvider = messageProvider;
  }
}
