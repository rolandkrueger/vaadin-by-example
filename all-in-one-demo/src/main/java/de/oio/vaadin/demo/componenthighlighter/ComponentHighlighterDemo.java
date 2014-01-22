package de.oio.vaadin.demo.componenthighlighter;

import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;

import com.vaadin.ui.Component;

import de.oio.vaadin.demo.AbstractDemo;
import de.oio.vaadin.demo.DemoInfo;

public class ComponentHighlighterDemo extends AbstractDemo {

	public ComponentHighlighterDemo(ITemplatingService templatingService,
			SessionContext context) {
		super(templatingService, context);
	}

	@Override
	public String getName() {
		return "ComponentHighlighterDemo";
	}

	@Override
	public DemoInfo getDemoInfo() {
		DemoInfo info = new DemoInfo();
		info.setBlogPostTitle("Vaadin Extension: Highlighting Custom Components During Development");
		info.setBlogPostURI("http://blog.oio.de/2014/01/21/vaadin-extension-highlighting-custom-components-during-development/");
		info.setCodeHostingURI("https://github.com/rolandkrueger/ComponentHighlighter");
		info.setShortDescriptionKey("componentHighlighter.shortDescription");
		info.setDemoHeadlineKey("componentHighlighter.headline");
		return info;
	}

	@Override
	public Component getView() {
		ComponentHighlighterDemoView view = new ComponentHighlighterDemoView();
		view.buildLayout();
		return view.getContent();
	}
}
