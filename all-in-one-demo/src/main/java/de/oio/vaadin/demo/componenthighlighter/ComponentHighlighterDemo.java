package de.oio.vaadin.demo.componenthighlighter;

import com.vaadin.ui.Component;
import de.oio.vaadin.demo.AbstractDemo;
import de.oio.vaadin.demo.DemoInfo;
import de.oio.vaadin.services.templating.TemplatingService;
import de.oio.vaadin.session.SessionContext;

public class ComponentHighlighterDemo extends AbstractDemo {

  public static final String DEMO_NAME = "ComponentHighlighterDemo";

  public ComponentHighlighterDemo(TemplatingService templatingService,
                                  SessionContext context) {
    super(templatingService, context);
  }

  @Override
  public String getName() {
    return DEMO_NAME;
  }

  @Override
  public DemoInfo getDemoInfo() {
    DemoInfo info = new DemoInfo();
    info.setBlogPostTitle("Vaadin Extension: Highlighting Custom Components During Development");
    info.setBlogPostURI("http://blog.oio.de/2014/01/21/vaadin-extension-highlighting-custom-components-during-development/");
    info.setCodeHostingURI("https://github.com/rolandkrueger/ComponentHighlighter");
    info.setShortDescriptionKey("componentHighlighter.shortDescription");
    info.setDemoHeadlineKey("componentHighlighter.headline");
    return info;
  }

  @Override
  public Component getView() {
    ComponentHighlighterDemoView view = new ComponentHighlighterDemoView(getTemplatingService().getLayoutTemplate("demos/componentHighlighterDemo"));
    view.buildLayout();
    return view.getContent();
  }
}
