package de.oio.vaadin.views.impl;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;
import de.oio.vaadin.DemoUI;
import de.oio.vaadin.components.TranslatedCustomLayout;
import de.oio.vaadin.demo.AbstractDemo;
import de.oio.vaadin.services.MessageProvider;
import de.oio.vaadin.services.application.UriActionMapperTreeService;
import de.oio.vaadin.services.templating.TemplateData;
import de.oio.vaadin.views.View;
import org.vaadin.highlighter.ComponentHighlighterExtension;

import java.util.Collection;

import static com.google.common.base.Preconditions.checkNotNull;

public class DemoSelectionView extends TranslatedCustomLayout {

  private Collection<AbstractDemo> demos;
  private MessageProvider messageProvider;
  private final UriActionMapperTreeService uriActionMapperTreeService;

  public DemoSelectionView(Collection<AbstractDemo> demos, MessageProvider messageProvider, TemplateData layoutData, UriActionMapperTreeService uriActionMapperTreeService) {
    super(layoutData);
    this.demos = checkNotNull(demos);
    this.messageProvider = checkNotNull(messageProvider);
    this.uriActionMapperTreeService = uriActionMapperTreeService;
  }

  @Override
  public View buildLayout() {
    super.buildLayout();
    DemoSelector selector = new DemoSelector(demos);

    getLayout().addComponent(selector, "selectionList");
    return this;
  }

  private class DemoSelector extends VerticalLayout {

    public DemoSelector(Collection<AbstractDemo> demos) {
      if (DemoUI.isDebugMode()) {
        new ComponentHighlighterExtension(this);
      }

      for (AbstractDemo demo : demos) {
        // FIXME: hard-coded #!

        Link link = new Link(messageProvider.getMessage(demo.getDemoInfo().getDemoHeadlineKey()),
            new ExternalResource("#!" + uriActionMapperTreeService.getUriActionMapperTree().assembleUriFragment(uriActionMapperTreeService.getActionMapperForName(demo.getName()))));
        link.addStyleName("demoSelectorLink");
        addComponent(link);
      }

      addStyleName("demoSelector");
    }
  }
}
