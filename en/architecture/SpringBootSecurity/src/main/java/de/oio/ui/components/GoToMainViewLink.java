package de.oio.ui.components;

import com.vaadin.server.ExternalResource;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Link;
import de.oio.ui.views.MainView;

public class GoToMainViewLink extends CustomComponent {
	public GoToMainViewLink() {
		Link goBackLink = new Link("Go back to main", new ExternalResource("#!" + MainView.NAME));
		goBackLink.setIcon(FontAwesome.HOME);
		setCompositionRoot(goBackLink);
	}
}
