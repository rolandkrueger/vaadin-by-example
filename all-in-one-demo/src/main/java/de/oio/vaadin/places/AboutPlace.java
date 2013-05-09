package de.oio.vaadin.places;

import org.vaadin.appbase.places.AbstractPlace;

import de.oio.vaadin.DemoUI;

public class AboutPlace extends AbstractPlace {

	public AboutPlace() {
		super("About");
	}

	@Override
	public void activate() {
		DemoUI.getCurrent().getViewManager().showAboutView();
	}

}
