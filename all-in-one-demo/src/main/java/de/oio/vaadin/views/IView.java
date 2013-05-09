package de.oio.vaadin.views;

import com.vaadin.ui.Component;

public interface IView {
	public void buildLayout();

	public Component getContent();
}
