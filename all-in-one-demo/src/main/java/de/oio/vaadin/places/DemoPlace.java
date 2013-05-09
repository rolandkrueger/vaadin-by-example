package de.oio.vaadin.places;

import org.vaadin.appbase.places.AbstractPlace;

import com.google.common.base.Preconditions;

import de.oio.vaadin.DemoUI;
import de.oio.vaadin.demo.AbstractDemo;

public class DemoPlace extends AbstractPlace {
	private AbstractDemo demo;

	public DemoPlace(AbstractDemo demo) {
		super("DemoPlace");
		Preconditions.checkNotNull(demo);
		this.demo = demo;
	}

	@Override
	public void activate() {
		DemoUI.getCurrent().getViewManager().showDemoSelectionView();
	}

}
