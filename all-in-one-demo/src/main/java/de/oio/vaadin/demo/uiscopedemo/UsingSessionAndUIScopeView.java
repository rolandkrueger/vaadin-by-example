package de.oio.vaadin.demo.uiscopedemo;

import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;

import de.oio.vaadin.views.CustomLayoutView;

public class UsingSessionAndUIScopeView extends CustomLayoutView {

	public UsingSessionAndUIScopeView(ITemplatingService templatingService,
			SessionContext context) {
		super(templatingService, context, "demos/UsingSessionAndUIScope");
	}

}
