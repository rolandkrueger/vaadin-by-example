package de.oio.ui.components;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.server.ExternalResource;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Link;
import de.oio.ui.MainUI;
import de.oio.ui.views.LogoutView;

public class LogoutLink extends CustomComponent {

	public LogoutLink() {
		Link logoutLink = new Link("Logout", new ExternalResource("#!" + LogoutView.NAME));
		logoutLink.setIcon(VaadinIcons.SIGN_OUT);
		setCompositionRoot(logoutLink);
	}

	public void updateVisibility() {
		setVisible(!MainUI.getCurrent().isUserAnonymous());
	}
}
