package de.oio.vaadin.places;

import com.google.common.base.Preconditions;
import de.oio.vaadin.DemoUI;
import org.vaadin.appbase.places.AbstractPlace;

public class DemoPlace extends AbstractPlace {
	private String demo;

	public DemoPlace(String demo) {
		super("DemoPlace");
		Preconditions.checkNotNull(demo);
		this.demo = demo;
	}

	@Override
	public void activate() {
		DemoUI.getCurrent().getViewManager().showDemoView(demo);
	}

}
