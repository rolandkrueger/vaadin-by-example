package de.oio.vaadin.mvp;

import java.util.Locale;

public interface IMainView {
	public void setPresenter(Presenter presenter);

	public void setCurrentLocale(Locale currentLocale);

	public interface Presenter {
		public void changeLanguage(Locale newLocale);
	}
}
