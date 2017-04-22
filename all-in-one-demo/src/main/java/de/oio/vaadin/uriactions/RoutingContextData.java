package de.oio.vaadin.uriactions;

import com.vaadin.server.VaadinService;
import de.oio.vaadin.event.EventBus;
import de.oio.vaadin.session.SessionContext;
import org.roklib.urifragmentrouting.UriActionMapperTree;

public class RoutingContextData {
  private EventBus eventBus;
  private UriActionMapperTree uriActionMapperTree;
  private SessionContext context;

  public RoutingContextData(EventBus eventBus, UriActionMapperTree uriActionMapperTree, SessionContext context) {
    this.eventBus = eventBus;
    this.uriActionMapperTree = uriActionMapperTree;
    this.context = context;
  }

  public EventBus getEventBus() {
    return eventBus;
  }

  public UriActionMapperTree getUriActionMapperTree() {
    return uriActionMapperTree;
  }

  public SessionContext getContext() {
    return context;
  }

  @Override
  public String toString() {
    return "RoutingContextData [RemoteAddr: " + VaadinService.getCurrentRequest().getRemoteAddr() + "]";
  }
}
