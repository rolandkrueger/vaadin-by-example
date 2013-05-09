package de.oio.vaadin.places;

import org.vaadin.appbase.places.AbstractPlace;

import de.oio.vaadin.DemoUI;

public class DemoSelectionPlace extends AbstractPlace {
	public DemoSelectionPlace() {
		super("Demos");
	}

	@Override
	public void activate() {
		DemoUI.getCurrent().getViewManager().showDemoSelectionView();
	}
}
