package de.oio.vaadin;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.appbase.VaadinUIServices;
import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;

import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.UI;

import de.oio.vaadin.demo.AbstractDemo;
import de.oio.vaadin.demo.customhighlighter.ComponentHighlighterDemo;
import de.oio.vaadin.demo.i18nForCustomLayoutsUsingVelocity.I18nForCustomLayoutsUsingVelocityDemo;
import de.oio.vaadin.demo.uiscopedemo.UsingSessionAndUIScopeDemo;
import de.oio.vaadin.manager.URIActionHandlerProvider;
import de.oio.vaadin.manager.ViewManager;

@Theme("demo")
@Configurable(preConstruction = true)
public class DemoUI extends UI {
	@Autowired
	private SessionContext context;
	@Autowired
	private ITemplatingService templatingService;
	@Getter
	private ViewManager viewManager;
	private URIActionHandlerProvider uriActionHandlerProvider;

	@Getter
	private Map<String, AbstractDemo> demos;

	@Override
	public void init(VaadinRequest request) {

		Page.getCurrent().setTitle("Vaadin By Example Demo");

		VaadinUIServices uiServices = new VaadinUIServices();
		uiServices.startUp();

		context.setLocale(getLocale());

		uriActionHandlerProvider = new URIActionHandlerProvider(
				uiServices.getUriActionManager());
		uriActionHandlerProvider.buildURILayout();

		viewManager = new ViewManager();
		viewManager.buildLayout(this);

		buildDemos();
	}

	private void buildDemos() {
		demos = new HashMap<>();
		addDemo(new UsingSessionAndUIScopeDemo(templatingService, context));
		addDemo(new I18nForCustomLayoutsUsingVelocityDemo(templatingService,
				context));
		addDemo(new ComponentHighlighterDemo(templatingService, context));

		uriActionHandlerProvider.registerDemos(demos.values());
		uriActionHandlerProvider.getUriActionManager().logActionOverview();
	}

	private void addDemo(AbstractDemo demo) {
		demos.put(demo.getName(), demo);
	}

	public static DemoUI getCurrent() {
		return (DemoUI) UI.getCurrent();
	}

	public static boolean isDebugMode() {
		return !VaadinService.getCurrent().getDeploymentConfiguration()
				.isProductionMode();
	}
}
