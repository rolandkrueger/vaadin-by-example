package de.oio.vaadin.views.impl;

import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;

import com.vaadin.ui.Component;

import de.oio.vaadin.views.CustomLayoutView;

public class MainView extends CustomLayoutView {

	public MainView(ITemplatingService templatingService, SessionContext context) {
		super(templatingService, context, "main");
	}

	public void setContent(Component content) {
		getLayout().addComponent(content, "_main_panel_");
	}
}
