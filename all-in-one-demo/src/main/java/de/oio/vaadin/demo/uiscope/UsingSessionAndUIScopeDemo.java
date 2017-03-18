package de.oio.vaadin.demo.uiscope;

import com.vaadin.ui.Component;
import de.oio.vaadin.demo.AbstractDemo;
import de.oio.vaadin.demo.DemoInfo;
import de.oio.vaadin.services.MessageProvider;
import de.oio.vaadin.services.application.UriActionMapperTreeService;
import de.oio.vaadin.services.templating.TemplatingService;
import de.oio.vaadin.session.SessionContext;

public class UsingSessionAndUIScopeDemo extends AbstractDemo {
  public final static String SESSION_SCOPED_VALUE_ID = "sessionScopedValue";
  public final static String DEMO_NAME = "UsingSessionAndUIScope";
  private final MessageProvider messageProvider;
  private final UriActionMapperTreeService uriActionMapperTreeService;

  public UsingSessionAndUIScopeDemo(TemplatingService templatingService, SessionContext context, MessageProvider messageProvider, UriActionMapperTreeService uriActionMapperTreeService) {
    super(templatingService, context);
    this.messageProvider = messageProvider;
    this.uriActionMapperTreeService = uriActionMapperTreeService;
  }

  @Override
  public String getName() {
    return DEMO_NAME;
  }

  @Override
  public DemoInfo getDemoInfo() {
    DemoInfo info = new DemoInfo();
    info.setBlogPostURI("http://blog.oio.de/2013/02/22/vaadins-variable-scopes-vaadinsession-and-ui/");
    info.setCodeHostingURI("https://github.com/rolandkrueger/vaadin-by-example/tree/master/en/architecture/UsingSessionAndUIScope");
    info.setBlogPostTitle("Vaadin's Variable Scopes: VaadinSession and UI");
    info.setShortDescriptionKey("UsingSessionAndUIScope.shortDescription");
    info.setDemoHeadlineKey("UsingSessionAndUIScope.headline");
    return info;
  }

  @Override
  public Component getView() {
    UsingSessionAndUIScopeView view = new UsingSessionAndUIScopeView(messageProvider, uriActionMapperTreeService, getTemplatingService()
        .getLayoutTemplate("demos/UsingSessionAndUIScope"));
    view.buildLayout();
    return view.getContent();
  }
}
