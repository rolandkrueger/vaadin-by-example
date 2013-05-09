package de.oio.vaadin.manager;

import org.roklib.webapps.uridispatching.AbstractURIActionHandler;
import org.vaadin.appbase.uriactions.URIActionManager;
import org.vaadin.appbase.uriactions.commands.RedirectActionCommand;
import org.vaadin.appbase.uriactions.handlers.SimplePlaceRequestActionHandler;

import com.google.common.base.Preconditions;

import de.oio.vaadin.places.AboutPlace;
import de.oio.vaadin.places.DemoSelectionPlace;
import de.oio.vaadin.places.HomePlace;

public class URIActionHandlerProvider {
	private URIActionManager uriActionManager;

	private AbstractURIActionHandler homeHandler;
	private AbstractURIActionHandler aboutHandler;
	private AbstractURIActionHandler demosHandler;

	public URIActionHandlerProvider(URIActionManager uriActionManager) {
		Preconditions.checkNotNull(uriActionManager);
		this.uriActionManager = uriActionManager;
	}

	public void buildURILayout() {
		homeHandler = new SimplePlaceRequestActionHandler("home",
				new HomePlace());

		aboutHandler = new SimplePlaceRequestActionHandler("about",
				new AboutPlace());

		demosHandler = new SimplePlaceRequestActionHandler("demos",
				new DemoSelectionPlace());

		uriActionManager.addHandler(homeHandler);
		uriActionManager.addHandler(aboutHandler);
		uriActionManager.addHandler(demosHandler);

		uriActionManager.setRootCommand(new RedirectActionCommand(homeHandler));
	}
}
