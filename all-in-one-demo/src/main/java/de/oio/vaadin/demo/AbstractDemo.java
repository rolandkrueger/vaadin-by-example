package de.oio.vaadin.demo;

import com.vaadin.ui.Component;
import org.roklib.urifragmentrouting.mapper.UriPathSegmentActionMapper;
import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;

public abstract class AbstractDemo {

  private ITemplatingService templatingService;
  private SessionContext context;

  private UriPathSegmentActionMapper uriActionManager;

  public AbstractDemo(ITemplatingService templatingService, SessionContext context) {
    super();
    this.templatingService = templatingService;
    this.context = context;
  }

  public abstract String getName();

  public abstract DemoInfo getDemoInfo();

  public abstract Component getView();

  protected ITemplatingService getTemplatingService() {
    return templatingService;
  }

  protected SessionContext getContext() {
    return context;
  }

}
