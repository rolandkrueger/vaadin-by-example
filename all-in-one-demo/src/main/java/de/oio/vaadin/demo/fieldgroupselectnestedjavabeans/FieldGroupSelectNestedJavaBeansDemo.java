package de.oio.vaadin.demo.fieldgroupselectnestedjavabeans;


import com.vaadin.ui.Component;
import de.oio.vaadin.demo.AbstractDemo;
import de.oio.vaadin.demo.DemoInfo;
import de.oio.vaadin.services.MessageProvider;
import de.oio.vaadin.services.templating.TemplatingService;
import de.oio.vaadin.session.SessionContext;

public class FieldGroupSelectNestedJavaBeansDemo extends AbstractDemo {

  public final static String DEMO_NAME = "FieldGroupSelectNestedJavaBeans";

  private final MessageProvider messageProvider;

  public FieldGroupSelectNestedJavaBeansDemo(TemplatingService templatingService, SessionContext context, MessageProvider messageProvider) {
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
    info.setBlogPostTitle("Select Nested JavaBeans With a Vaadin FieldGroup");
    info.setBlogPostURI("http://blog.oio.de/2014/04/25/select-nested-javabeans-vaadin-fieldgroup/");
    info.setCodeHostingURI("https://github.com/rolandkrueger/vaadin-by-example/tree/master/en/ui/NestedJavaBeansInAVaadinFieldGroup");
    info.setShortDescriptionKey("FieldGroupSelectNestedJavaBeans.shortDescription");
    info.setDemoHeadlineKey("FieldGroupSelectNestedJavaBeans.headline");
    return info;
  }

  @Override
  public Component getView() {
    FieldGroupSelectNestedJavaBeansView view = new FieldGroupSelectNestedJavaBeansView(getTemplatingService().getLayoutTemplate("demos/FieldGroupSelectNestedJavaBeans"), messageProvider);
    view.buildLayout();
    return view.getContent();
  }

}
