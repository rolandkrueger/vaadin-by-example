package de.oio.vaadin.demo.i18nforcustomlayoutsusingthymeleaf;

import com.vaadin.ui.Component;
import de.oio.vaadin.demo.AbstractDemo;
import de.oio.vaadin.demo.DemoInfo;
import de.oio.vaadin.services.templating.TemplatingService;
import de.oio.vaadin.session.SessionContext;

public class I18nForCustomLayoutsUsingThymeleafDemo extends AbstractDemo {

  public static final String DEMO_NAME = "i18nForCustomLayoutsUsingVelocity";

  public I18nForCustomLayoutsUsingThymeleafDemo(
      TemplatingService templatingService, SessionContext context) {
    super(templatingService, context);
  }

  @Override
  public String getName() {
    return DEMO_NAME;
  }

  @Override
  public DemoInfo getDemoInfo() {
    DemoInfo info = new DemoInfo();
    info.setBlogPostTitle("i18n for Vaadin Applications Using CustomLayouts and Apache Velocity");
    info.setBlogPostURI("http://blog.oio.de/2013/05/07/i18n-for-vaadin-applications-using-customlayouts-and-apache-velocity/");
    info.setCodeHostingURI("https://github.com/rolandkrueger/vaadin-by-example/tree/master/en/i18n/i18nForCustomLayoutsUsingVelocity");
    info.setShortDescriptionKey("i18nForCustomLayoutsUsingThymeleaf.shortDescription");
    info.setDemoHeadlineKey("i18nForCustomLayoutsUsingThymeleaf.headline");
    return info;
  }

  @Override
  public Component getView() {
    I18nForCustomLayoutsUsingThymeleafView view = new I18nForCustomLayoutsUsingThymeleafView(getTemplatingService()
        .getLayoutTemplate("demos/i18nForCustomLayoutsUsingThymeleaf"));
    view.buildLayout();
    return view.getContent();
  }

}
