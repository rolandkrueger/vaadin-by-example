package de.oio.vaadin.demo.urifragmentactions;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;
import de.oio.vaadin.demo.AbstractDemo;
import de.oio.vaadin.demo.DemoInfo;
import de.oio.vaadin.services.templating.TemplatingService;
import de.oio.vaadin.session.SessionContext;

public class UriFragmentActionsDemo extends AbstractDemo {
  public final static String DEMO_NAME = "UriFragmentActions";

  public UriFragmentActionsDemo(TemplatingService templatingService, SessionContext context) {
    super(templatingService, context);
  }

  @Override
  public String getName() {
    return DEMO_NAME;
  }

  @Override
  public DemoInfo getDemoInfo() {
    DemoInfo info = new DemoInfo();
    info.setCodeHostingURI("https://github.com/rolandkrueger/vaadin-by-example/tree/master/en/ui/URIFragmentActionsForVaadinDemo");
    info.setShortDescriptionKey("UriFragmentActions.shortDescription");
    info.setDemoHeadlineKey("UriFragmentActions.headline");
    return info;
  }

  @Override
  public Component getView() {
    return new Label("");
  }
}
