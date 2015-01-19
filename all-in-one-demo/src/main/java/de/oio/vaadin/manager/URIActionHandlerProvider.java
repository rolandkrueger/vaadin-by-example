package de.oio.vaadin.manager;

import de.oio.vaadin.demo.AbstractDemo;
import de.oio.vaadin.places.AboutPlace;
import de.oio.vaadin.places.DemoPlace;
import de.oio.vaadin.places.DemoSelectionPlace;
import de.oio.vaadin.places.HomePlace;
import org.roklib.webapps.uridispatching.AbstractURIActionHandler;
import org.roklib.webapps.uridispatching.DispatchingURIActionHandler;
import org.vaadin.appbase.event.IEventBus;
import org.vaadin.appbase.uriactions.URIActionManager;
import org.vaadin.appbase.uriactions.commands.RedirectActionCommand;
import org.vaadin.appbase.uriactions.handlers.SimplePlaceRequestActionHandler;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.base.Preconditions.*;

public class URIActionHandlerProvider {
  private final URIActionManager uriActionManager;
  private final IEventBus eventBus;

  private AbstractURIActionHandler homeHandler;
  private AbstractURIActionHandler aboutHandler;
  private AbstractURIActionHandler demosHandler;
  private DispatchingURIActionHandler demoHandler;
  private Map<String, AbstractURIActionHandler> demoHandlersMap;

  public URIActionHandlerProvider(URIActionManager uriActionManager, IEventBus eventBus) {
    this.eventBus = checkNotNull(eventBus);
    this.uriActionManager = checkNotNull(uriActionManager);
    demoHandlersMap = new HashMap<String, AbstractURIActionHandler>();
  }

  public void buildURILayout() {
    homeHandler = new SimplePlaceRequestActionHandler("home", new HomePlace(), eventBus);

    aboutHandler = new SimplePlaceRequestActionHandler("about", new AboutPlace(), eventBus);

    demosHandler = new SimplePlaceRequestActionHandler("demos", new DemoSelectionPlace(), eventBus);

    demoHandler = new DispatchingURIActionHandler("demo");

    uriActionManager.addHandler(homeHandler);
    uriActionManager.addHandler(aboutHandler);
    uriActionManager.addHandler(demosHandler);
    uriActionManager.addHandler(demoHandler);

    uriActionManager.setRootCommand(new RedirectActionCommand(homeHandler, eventBus));
  }

  public void registerDemos(Collection<AbstractDemo> demos) {
    for (AbstractDemo demo : demos) {
      AbstractURIActionHandler handler = new SimplePlaceRequestActionHandler(demo.getName(), new DemoPlace(demo), eventBus);
      demoHandler.addSubHandler(handler);
      demo.setUriHandler(handler);
      demoHandlersMap.put(demo.getName(), handler);
    }
  }

  public URIActionManager getUriActionManager() {
    return uriActionManager;
  }

  public Map<String, AbstractURIActionHandler> getDemoHandlersMap() {
    return demoHandlersMap;
  }
}
