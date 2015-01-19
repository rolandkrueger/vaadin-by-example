package de.oio.vaadin.views.impl;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import de.oio.vaadin.DemoUI;
import de.oio.vaadin.demo.AbstractDemo;
import org.vaadin.appbase.components.TranslatedCustomLayout;
import org.vaadin.appbase.service.IMessageProvider;
import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.view.IView;
import org.vaadin.highlighter.ComponentHighlighterExtension;

import static com.google.common.base.Preconditions.*;

public class DemoView extends TranslatedCustomLayout {

  private AbstractDemo demo;
  private IMessageProvider messageProvider;
  private final ITemplatingService templatingService;

  public DemoView(AbstractDemo demo, IMessageProvider messageProvider, ITemplatingService templatingService) {
    super(templatingService.getLayoutTemplate("demo"));
    this.templatingService = checkNotNull(templatingService);
    this.demo = checkNotNull(demo);
    this.messageProvider = checkNotNull(messageProvider);
  }

  @Override
  public IView buildLayout() {
    super.buildLayout();

    DemoInfoPanel demoInfoPanel = new DemoInfoPanel();
    demoInfoPanel.buildLayout();
    getLayout().addComponent(demoInfoPanel.getContent(), "descriptionPanel");
    getLayout().addComponent(demo.getView(), "mainPanel");
    return this;
  }

  private class DemoInfoPanel extends TranslatedCustomLayout {
    public DemoInfoPanel() {
      super(templatingService.getLayoutTemplate("demoInfo"));
    }

    @Override
    public IView buildLayout() {
      super.buildLayout();
      if (DemoUI.isDebugMode()) {
        new ComponentHighlighterExtension(getLayout());
      }

      getLayout().addComponent(new Label(messageProvider.getMessage(demo.getDemoInfo().getDemoHeadlineKey())),
          "demoHeadline");
      getLayout().addComponent(
          new Link(demo.getDemoInfo().getBlogPostTitle(), new ExternalResource(demo.getDemoInfo().getBlogPostURI())),
          "linkToBlogPost");
      getLayout()
          .addComponent(
              new Link(demo.getDemoInfo().getCodeHostingURI(), new ExternalResource(demo.getDemoInfo()
                  .getCodeHostingURI())), "linkToDemoCode");
      getLayout().addComponent(new Label(messageProvider.getMessage(demo.getDemoInfo().getShortDescriptionKey())),
          "shortDescription");
      return this;
    }
  }
}
