package de.oio.vaadin.places;

import org.vaadin.appbase.places.AbstractPlace;

import de.oio.vaadin.DemoUI;

public class HomePlace extends AbstractPlace {

	public HomePlace() {
		super("home");
	}

	@Override
	public void activate() {
		DemoUI.getCurrent().getViewManager().showHomeView();
	}

}
