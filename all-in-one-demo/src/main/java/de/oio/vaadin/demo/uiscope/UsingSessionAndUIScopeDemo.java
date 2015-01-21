package de.oio.vaadin.demo.uiscope;

import org.vaadin.appbase.service.IMessageProvider;
import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;

import com.vaadin.ui.Component;

import de.oio.vaadin.demo.AbstractDemo;
import de.oio.vaadin.demo.DemoInfo;

public class UsingSessionAndUIScopeDemo extends AbstractDemo {
  public final static String SESSION_SCOPED_VALUE_ID = "sessionScopedValue";
  public final static String DEMO_NAME = "UsingSessionAndUIScope";
  private final IMessageProvider messageProvider;

  public UsingSessionAndUIScopeDemo(ITemplatingService templatingService, SessionContext context, IMessageProvider messageProvider) {
    super(templatingService, context);
    this.messageProvider = messageProvider;
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
    UsingSessionAndUIScopeView view = new UsingSessionAndUIScopeView(messageProvider, getTemplatingService()
            .getLayoutTemplate("demos/UsingSessionAndUIScope"));
    view.buildLayout();
    return view.getContent();
  }
}
