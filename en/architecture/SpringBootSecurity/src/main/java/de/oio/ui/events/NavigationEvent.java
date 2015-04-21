package de.oio.ui.events;

import java.util.EventObject;

public class NavigationEvent extends EventObject {

	private String target;

	public NavigationEvent(Object source, String target) {
		super(source);
		this.target = target == null ? "" : target;
	}

	public String getTarget() {
		return target;
	}
}
