package de.oio.vaadin.views.impl;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Component;
import de.oio.vaadin.components.LanguageSelector;
import de.oio.vaadin.components.TranslatedCustomLayout;
import de.oio.vaadin.mvp.MainView;
import de.oio.vaadin.services.MessageProvider;
import de.oio.vaadin.services.templating.TemplateData;
import de.oio.vaadin.views.View;

import java.util.Locale;

public class MainViewImpl extends TranslatedCustomLayout implements MainView,
    Property.ValueChangeListener {
  private MessageProvider messageProvider;
  private MainView.Presenter presenter;
  private LanguageSelector languageSelector;

  public MainViewImpl(MessageProvider messageProvider, TemplateData layoutData) {
    super(layoutData);
    this.messageProvider = messageProvider;
  }

  public void setContent(Component content) {
    getLayout().addComponent(content, "_main_panel_");
  }

  @Override
  public View buildLayout() {
    super.buildLayout();
    languageSelector = new LanguageSelector(messageProvider);
    languageSelector.addValueChangeListener(this);
    getLayout().addComponent(languageSelector, "languageSelector");
    return this;
  }

  @Override
  public void setPresenter(Presenter presenter) {
    this.presenter = presenter;
  }

  @Override
  public void valueChange(ValueChangeEvent event) {
    Locale locale = (Locale) event.getProperty().getValue();
    presenter.changeLanguage(locale);
  }

  @Override
  public void setCurrentLocale(Locale currentLocale) {
    languageSelector.setValue(new Locale(currentLocale.getLanguage()));
  }
}
