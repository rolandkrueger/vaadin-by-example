package de.oio.vaadin.demo.customhighlighter;

import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;

import de.oio.vaadin.views.CustomLayoutView;

public class ComponentHighlighterDemoView extends CustomLayoutView {

	public ComponentHighlighterDemoView(ITemplatingService templatingService,
			SessionContext context) {
		super(templatingService, context, "demos/componentHighlighterDemo");
	}

}
