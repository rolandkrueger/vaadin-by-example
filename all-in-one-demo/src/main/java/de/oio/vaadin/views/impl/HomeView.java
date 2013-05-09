package de.oio.vaadin.views.impl;

import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;

import de.oio.vaadin.views.CustomLayoutView;

public class HomeView extends CustomLayoutView {

	public HomeView(ITemplatingService templatingService, SessionContext context) {
		super(templatingService, context, "home");
	}

}
