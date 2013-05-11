package de.oio.vaadin.demo.customhighlighter;

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
		info.setBlogPostTitle("<To be done>");
		info.setBlogPostURI("http://blog.oio.de");
		info.setCodeHostingURI("https://github.com/rolandkrueger/ComponentHighlighter");
		info.setShortDescriptionKey("componentHighlighter.shortDescription");
		return info;
	}

	@Override
	public Component getView() {
		ComponentHighlighterDemoView view = new ComponentHighlighterDemoView(
				getTemplatingService(), getContext());
		view.buildLayout();
		return view.getContent();
	}
}
