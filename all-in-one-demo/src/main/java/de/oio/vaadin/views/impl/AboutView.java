package de.oio.vaadin.views.impl;

import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;

import de.oio.vaadin.views.CustomLayoutView;

public class AboutView extends CustomLayoutView {

	public AboutView(ITemplatingService templatingService,
			SessionContext context) {
		super(templatingService, context, "about");
	}

}
