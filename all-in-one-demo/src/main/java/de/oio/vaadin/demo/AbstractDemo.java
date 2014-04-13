package de.oio.vaadin.demo;

import org.roklib.webapps.uridispatching.AbstractURIActionHandler;
import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;

import com.vaadin.ui.Component;

public abstract class AbstractDemo {

  private ITemplatingService templatingService;
  private SessionContext context;

  private AbstractURIActionHandler uriHandler;

  public AbstractDemo(ITemplatingService templatingService, SessionContext context) {
    super();
    this.templatingService = templatingService;
    this.context = context;
  }

  public abstract String getName();

  public abstract DemoInfo getDemoInfo();

  public abstract Component getView();

  public AbstractURIActionHandler getUriHandler() {
    return uriHandler;
  }

  public void setUriHandler(AbstractURIActionHandler uriHandler) {
    this.uriHandler = uriHandler;
  }

  protected ITemplatingService getTemplatingService() {
    return templatingService;
  }

  protected SessionContext getContext() {
    return context;
  }

}
