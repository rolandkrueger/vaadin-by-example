package de.oio.vaadin.client.widgets;

import com.google.gwt.user.client.ui.Image;
import com.vaadin.client.ui.VButton;

/**
 * Schaltfläche zum Zurücksetzen des Textfeldes. Der Button kann von der
 * clientseitigen Vaadin-Buttonklasse abgeleitet werden.
 */
public class ResetButton extends VButton {

	public ResetButton(String caption) {
		setText(caption);

		// wir erzeugen ein Image-Objekt und initialisieren dies mit der Icon
		// Ressource aus dem Client Bundle
		Image resetIcon = new Image(IconResources.INSTANCE.resetIcon());

		// dem Icon geben wir denselben CSS Stylenamen, wie er von Vaadin für
		// Icons verwendet wird
		resetIcon.setStylePrimaryName("v-icon");

		// mit Hilfe von GWT-Bordmitteln platzieren wir das Icon-Element vor die
		// Beschriftung des Buttons
		super.wrapper.insertBefore(resetIcon.getElement(), super.captionElement);
	}
}
