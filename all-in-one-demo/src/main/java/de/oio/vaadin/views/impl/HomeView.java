package de.oio.vaadin.views.impl;

import org.vaadin.appbase.components.CustomLayoutView;
import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;


public class HomeView extends CustomLayoutView {

	public HomeView(ITemplatingService templatingService, SessionContext context) {
		super(templatingService, context, "home");
	}

}
