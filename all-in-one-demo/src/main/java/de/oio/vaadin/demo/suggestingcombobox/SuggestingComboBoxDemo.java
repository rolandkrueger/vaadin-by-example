package de.oio.vaadin.demo.suggestingcombobox;

import com.vaadin.ui.Component;
import de.oio.vaadin.demo.AbstractDemo;
import de.oio.vaadin.demo.DemoInfo;
import de.oio.vaadin.demo.suggestingcombobox.component.WikipediaPageTitleAccessService;
import de.oio.vaadin.services.MessageProvider;
import de.oio.vaadin.services.templating.TemplatingService;
import de.oio.vaadin.session.SessionContext;

public class SuggestingComboBoxDemo extends AbstractDemo {

  public final static String DEMO_NAME = "SuggestingComboBox";

  private final MessageProvider messageProvider;
  private final WikipediaPageTitleAccessService wikipediaPageTitleAccessService;

  public SuggestingComboBoxDemo(TemplatingService templatingService, SessionContext context,
                                WikipediaPageTitleAccessService wikipediaPageTitleAccessService, MessageProvider messageProvider) {
    super(templatingService, context);
    this.messageProvider = messageProvider;
    this.wikipediaPageTitleAccessService = wikipediaPageTitleAccessService;
  }

  @Override
  public String getName() {
    return DEMO_NAME;
  }

  @Override
  public DemoInfo getDemoInfo() {
    DemoInfo info = new DemoInfo();
    info.setBlogPostTitle("How to get autocomplete suggestions from the database for Vaadin's ComboBox");
    info.setBlogPostURI("http://blog.oio.de/2015/01/17/write-simple-auto-complete-combobox-vaadin/");
    info.setCodeHostingURI("https://github.com/rolandkrueger/vaadin-by-example/tree/master/en/ui/SuggestingComboBox");
    info.setShortDescriptionKey("SuggestingComboBox.shortDescription");
    info.setDemoHeadlineKey("SuggestingComboBox.headline");
    return info;
  }

  @Override
  public Component getView() {
    SuggestingComboBoxView view = new SuggestingComboBoxView(getTemplatingService()
        .getLayoutTemplate("demos/SuggestingComboBox"), wikipediaPageTitleAccessService, messageProvider);
    view.buildLayout();
    return view.getContent();
  }
}
