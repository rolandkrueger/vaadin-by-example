package de.oio.vaadin.views.impl;

import org.vaadin.appbase.components.CustomLayoutView;
import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;


public class AboutView extends CustomLayoutView {

	public AboutView(ITemplatingService templatingService,
			SessionContext context) {
		super(templatingService, context, "about");
	}

}
