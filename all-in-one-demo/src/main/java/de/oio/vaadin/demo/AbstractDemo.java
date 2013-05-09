package de.oio.vaadin.demo;

import com.vaadin.ui.Component;

public abstract class AbstractDemo {

	public abstract String getName();

	public abstract DemoInfo getDemoInfo();

	public abstract Component getView();
}
