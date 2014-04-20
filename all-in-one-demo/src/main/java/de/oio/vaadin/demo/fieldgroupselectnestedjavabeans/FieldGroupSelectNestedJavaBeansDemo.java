package de.oio.vaadin.demo.fieldgroupselectnestedjavabeans;

import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;

import com.vaadin.ui.Component;

import de.oio.vaadin.demo.AbstractDemo;
import de.oio.vaadin.demo.DemoInfo;

public class FieldGroupSelectNestedJavaBeansDemo extends AbstractDemo {

  public final static String DEMO_NAME = "FieldGroupSelectNestedJavaBeans";

  private DemoInfo info;

  public FieldGroupSelectNestedJavaBeansDemo(ITemplatingService templatingService, SessionContext context) {
    super(templatingService, context);
    info = new DemoInfo();
    info.setBlogPostTitle("Select Nested JavaBeans With a Vaadin FieldGroup");
    info.setBlogPostURI("http://blog.oio.de/2014/04/10/select-nested-javabeans-vaadin-fieldgroup");
    info.setCodeHostingURI("https://github.com/rolandkrueger/vaadin-by-example/tree/master/en/ui/NestedJavaBeansInAVaadinFieldGroup");
    info.setShortDescriptionKey("FieldGroupSelectNestedJavaBeans.shortDescription");
    info.setDemoHeadlineKey("FieldGroupSelectNestedJavaBeans.headline");
  }

  @Override
  public String getName() {
    return DEMO_NAME;
  }

  @Override
  public DemoInfo getDemoInfo() {
    return info;
  }

  @Override
  public Component getView() {
    FieldGroupSelectNestedJavaBeansView view = new FieldGroupSelectNestedJavaBeansView();
    view.buildLayout();
    return view.getContent();
  }

}
