package de.oio.vaadin.mvp;

import java.util.Locale;

public interface MainView {
  void setPresenter(Presenter presenter);
  void setCurrentLocale(Locale currentLocale);

  interface Presenter {
    void changeLanguage(Locale newLocale);
  }
}
