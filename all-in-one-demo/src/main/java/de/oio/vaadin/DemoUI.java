package de.oio.vaadin;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.vaadin.appbase.VaadinUIServices;
import org.vaadin.appbase.service.templating.ITemplatingService;
import org.vaadin.appbase.session.SessionContext;

import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

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

	@Override
	public void init(VaadinRequest request) {
		Page.getCurrent().setTitle("Vaadin By Example Demo");

		VaadinUIServices uiServices = new VaadinUIServices();
		uiServices.startUp();

		templatingService
				.setResourceLoaderRoot("http://localhost:8080/vaadin-by-example-demo/VAADIN/themes/demo/layouts/");
		templatingService.setBundleNames("messages,general");

		context.setLocale(getLocale());

		uriActionHandlerProvider = new URIActionHandlerProvider(
				uiServices.getUriActionManager());
		uriActionHandlerProvider.buildURILayout();

		viewManager = new ViewManager();
		viewManager.buildLayout(this);
	}

	public static DemoUI getCurrent() {
		return (DemoUI) UI.getCurrent();
	}
}
