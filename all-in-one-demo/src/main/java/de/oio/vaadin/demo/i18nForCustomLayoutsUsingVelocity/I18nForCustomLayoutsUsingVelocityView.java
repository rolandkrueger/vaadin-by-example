package de.oio.vaadin.demo.i18nForCustomLayoutsUsingVelocity;

import org.vaadin.appbase.components.CustomLayoutView;
import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;


public class I18nForCustomLayoutsUsingVelocityView extends CustomLayoutView {

	public I18nForCustomLayoutsUsingVelocityView(
			ITemplatingService templatingService, SessionContext context) {
		super(templatingService, context,
				"demos/i18nForCustomLayoutsUsingVelocity");
	}

}
