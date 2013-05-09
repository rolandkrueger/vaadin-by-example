package de.oio.vaadin.demo.uiscopedemo;

import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;

import com.vaadin.ui.Component;

import de.oio.vaadin.demo.AbstractDemo;
import de.oio.vaadin.demo.DemoInfo;

public class UsingSessionAndUIScopeDemo extends AbstractDemo {
	private DemoInfo info;

	public UsingSessionAndUIScopeDemo(ITemplatingService templatingService,
			SessionContext context) {
		super(templatingService, context);
		info = new DemoInfo();
		info.setBlogPostURI("http://blog.oio.de/2013/02/22/vaadins-variable-scopes-vaadinsession-and-ui/");
		info.setCodeHostingURI("https://github.com/rolandkrueger/vaadin-by-example/tree/master/en/architecture/UsingSessionAndUIScope");
		info.setBlogPostTitle("Vaadin’s Variable Scopes: VaadinSession and UI");
		info.setShortDescription("This demo application demonstrates the two different scopes of a Vaadin 7 session. These are the common session scope and the new UI scope. The UI scope represents the data needed for the contents of one browser window or tab opened from the same session. This application lets you edit one session-scoped and one UI-scoped variable. It then demonstrates that for each browser window opened from the same session, a new UI object is created that contains its own version of the UI-scoped variable.");
	}

	@Override
	public String getName() {
		return "UsingSessionAndUIScope";
	}

	@Override
	public DemoInfo getDemoInfo() {
		return info;
	}

	@Override
	public Component getView() {
		UsingSessionAndUIScopeView view = new UsingSessionAndUIScopeView(
				getTemplatingService(), getContext());
		view.buildLayout();
		return view.getContent();
	}

}
