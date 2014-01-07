package de.oio.vaadin.views.impl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.appbase.components.TranslatedCustomLayout;
import org.vaadin.appbase.service.IMessageProvider;
import org.vaadin.appbase.view.IView;
import org.vaadin.highlighter.ComponentHighlighterExtension;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Link;
import com.vaadin.ui.VerticalLayout;

import de.oio.vaadin.DemoUI;
import de.oio.vaadin.demo.AbstractDemo;

@Configurable
public class DemoSelectionView extends TranslatedCustomLayout {

  private Collection<AbstractDemo> demos;
  @Autowired
  private IMessageProvider messageProvider;

  public DemoSelectionView(Collection<AbstractDemo> demos) {
    super("demoselection");
    this.demos = demos;
  }

  @Override
  public IView buildLayout() {
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
        Link link = new Link(messageProvider.getMessage(demo.getDemoInfo().getDemoHeadlineKey()), new ExternalResource(
            "#!" + demo.getUriHandler().getParameterizedActionURI(true).toString()));
        link.addStyleName("demoSelectorLink");
        addComponent(link);
      }

      addStyleName("demoSelector");
    }
  }
}
