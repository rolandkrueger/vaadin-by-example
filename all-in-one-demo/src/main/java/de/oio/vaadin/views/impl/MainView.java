package de.oio.vaadin.views.impl;

import java.util.Locale;

import org.vaadin.appbase.components.TranslatedCustomLayout;
import org.vaadin.appbase.service.IMessageProvider;
import org.vaadin.appbase.view.IView;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Component;

import de.oio.vaadin.components.LanguageSelector;
import de.oio.vaadin.mvp.IMainView;

public class MainView extends TranslatedCustomLayout implements IMainView,
		Property.ValueChangeListener {
	private IMessageProvider messageProvider;
	private IMainView.Presenter presenter;
	private LanguageSelector languageSelector;

  public MainView(IMessageProvider messageProvider) {
    super("main");
    this.messageProvider = messageProvider;
  }

  public void setContent(Component content) {
    getLayout().addComponent(content, "_main_panel_");
  }

  @Override
  public IView buildLayout() {
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
