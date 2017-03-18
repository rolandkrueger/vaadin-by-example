package de.oio.vaadin.demo;

import com.vaadin.ui.Component;
import de.oio.vaadin.services.templating.TemplatingService;
import de.oio.vaadin.session.SessionContext;
import org.roklib.urifragmentrouting.mapper.UriPathSegmentActionMapper;

public abstract class AbstractDemo {

  private TemplatingService templatingService;
  private SessionContext context;

  private UriPathSegmentActionMapper uriActionManager;

  public AbstractDemo(TemplatingService templatingService, SessionContext context) {
    super();
    this.templatingService = templatingService;
    this.context = context;
  }

  public abstract String getName();

  public abstract DemoInfo getDemoInfo();

  public abstract Component getView();

  protected TemplatingService getTemplatingService() {
    return templatingService;
  }

  protected SessionContext getContext() {
    return context;
  }

}
