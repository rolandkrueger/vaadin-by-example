package de.oio.vaadin.demo.componenthighlighter;

import org.vaadin.appbase.components.CustomLayoutView;
import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;


public class ComponentHighlighterDemoView extends CustomLayoutView {

	public ComponentHighlighterDemoView(ITemplatingService templatingService,
			SessionContext context) {
		super(templatingService, context, "demos/componentHighlighterDemo");
	}

}
