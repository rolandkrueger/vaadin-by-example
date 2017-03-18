package de.oio.vaadin.demo.suggestingcombobox;

import com.vaadin.data.Property;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Link;
import de.oio.vaadin.components.TranslatedCustomLayout;
import de.oio.vaadin.demo.suggestingcombobox.component.SuggestingComboBox;
import de.oio.vaadin.demo.suggestingcombobox.component.SuggestingContainer;
import de.oio.vaadin.demo.suggestingcombobox.component.WikipediaPage;
import de.oio.vaadin.demo.suggestingcombobox.component.WikipediaPageTitleAccessService;
import de.oio.vaadin.services.MessageProvider;
import de.oio.vaadin.services.templating.TemplateData;
import de.oio.vaadin.views.View;

public class SuggestingComboBoxView extends TranslatedCustomLayout {

  private Link wikipediaLink;
  private SuggestingComboBox comboBox;
  private final MessageProvider messageProvider;
  private final WikipediaPageTitleAccessService wikipediaPageTitleAccessService;

  public SuggestingComboBoxView(TemplateData templateData, WikipediaPageTitleAccessService
      wikipediaPageTitleAccessService, MessageProvider messageProvider) {
    super(templateData);
    this.messageProvider = messageProvider;
    this.wikipediaPageTitleAccessService = wikipediaPageTitleAccessService;
  }

  @Override
  public View buildLayout() {
    super.buildLayout();
    final SuggestingContainer container = new SuggestingContainer(wikipediaPageTitleAccessService);
    comboBox = new SuggestingComboBox();
    comboBox.setImmediate(true);
    comboBox.setContainerDataSource(container);
    comboBox.addValueChangeListener(new Property.ValueChangeListener() {
      @Override
      public void valueChange(Property.ValueChangeEvent event) {
        final WikipediaPage selectedPage = (WikipediaPage) comboBox.getValue();
        if (selectedPage != null) {
          wikipediaLink.setResource(new ExternalResource("http://en.wikipedia.org/wiki/" + selectedPage.getTitle()
              .replace
                  (' ', '_')));
          wikipediaLink.setCaption(messageProvider.getMessage("SuggestingComboBox.linkToPage") + selectedPage.getTitle() + "'");
        } else {
          wikipediaLink.setResource(new ExternalResource("http://en.wikipedia.org/"));
          wikipediaLink.setCaption(messageProvider.getMessage("SuggestingComboBox.linkToWikipedia"));
        }
        container.setSelected(selectedPage);
      }
    });

    wikipediaLink = new Link(messageProvider.getMessage("SuggestingComboBox.linkToWikipedia"), new ExternalResource("http://en.wikipedia.org/"));
    wikipediaLink.setTargetName("_blank");

    getLayout().addComponent(comboBox, "combobox");
    getLayout().addComponent(wikipediaLink, "link");
    return this;
  }
}
