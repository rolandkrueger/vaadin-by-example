package de.oio.vaadin.client.connectors;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.client.communication.StateChangeEvent;
import com.vaadin.client.ui.AbstractComponentConnector;
import com.vaadin.shared.ui.Connect;

import de.oio.vaadin.client.rpc.ResettableTextAreaServerRpc;
import de.oio.vaadin.client.widgets.VResettableTextArea;
import de.oio.vaadin.components.ResettableTextArea;

/**
 * Connector-Klasse für das clientseitige Widget {@link VResettableTextArea}.
 * Die Klasse implementiert einen {@link BlurHandler}, damit darauf reagiert
 * werden kann, wenn das Textfeld den Eingabefokus verliert. In diesem Fall wird
 * der aktuelle Inhalt des Textfeldes (lazy) an den Server geschickt und im
 * Local Storage des Browsers gespeichert. Über @Connect wird die Verbindung des
 * Connectors mit der dazugehörigen serverseitigen Komponente hergestellt.
 */
@Connect(ResettableTextArea.class)
public class ResettableTextAreaConnector extends AbstractComponentConnector implements BlurHandler {

	public ResettableTextAreaConnector() {
		// BlurHandler für das Textfeld registieren
		getWidget().getTextArea().addBlurHandler(this);

		// Neuen ClickHandler für den ResetButton registrieren.
		getWidget().getResetButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				// Wenn der ResetButton geklickt wurde, soll der (leere) Inhalt
				// des Textfeldes ebenfalls an den Server geschickt werden.
				// Andernfalls würde der Server nicht mitbekommen, dass das
				// Textfeld geleert wurde.
				sendCurrentText();
			}
		});
	}

	/**
	 * Diese Methode muss überschrieben werden, um dem Framework mitzuteilen,
	 * von welchem Typ das Widget ist, das von diesem Connector verwaltet wird.
	 */
	@Override
	public VResettableTextArea getWidget() {
		return (VResettableTextArea) super.getWidget();
	}

	/**
	 * Diese Methode muss überschrieben werden, um das Verfahren festzulegen,
	 * mit dem das Framework eine neue Instanz des Widgets anlegen kann.
	 */
	@Override
	protected Widget createWidget() {
		return GWT.create(VResettableTextArea.class);
	}

	/**
	 * Diese Methode muss überschrieben werden, um dem Framework den Typ der
	 * verwendeten Shared State-Klasse mitzuteilen.
	 */
	@Override
	public ResettableTextAreaState getState() {
		return (ResettableTextAreaState) super.getState();
	}

	/**
	 * Diese Methode wird immer dann aufgerufen, wenn der Shared State für die
	 * Komponente serverseitig geändert wurde. Wir überschreiben die Methode und
	 * leiten den geänderten Zustand an unser Widget weiter.
	 */
	@Override
	public void onStateChanged(StateChangeEvent stateChangeEvent) {
		super.onStateChanged(stateChangeEvent);
		// Wir lesen den Text aus dem Shared State-Objekt aus und übergeben
		// diesen an das Textfeld.
		getWidget().setText(getState().text);
	}

	/**
	 * Wenn das Textfeld den Eingabefokus verliert, senden wir dessen aktuellen
	 * Inhalt an den Server und aktualisieren den Local Storage des Browsers
	 * entsprechend.
	 */
	@Override
	public void onBlur(BlurEvent blurEvent) {
		sendCurrentText();
		// Wir müssen stets über die Methode getWidget() auf die vom Framework
		// verwaltete Instanz unseres Widgets zugreifen.
		getWidget().saveContentToLocalStorage();
	}

	/**
	 * Sendet den aktuellen Inhalt des Textfelds an den Server, damit dieser
	 * entsprechend das Shared State-Objekt aktualisieren kann. Der entfernte
	 * Methodenaufruf wird nicht sofort an den Server geschickt, sondern erst
	 * wenn eine andere Aktion (z.B. Klicken eines Buttons) ausgeführt wurde,
	 * die in jedem Fall zu einem Server Request führt. Dafür sorgt die
	 * Delayed-Annotation in dem Server-RPC Interface.
	 */
	private void sendCurrentText() {
		// Wir holen uns ein Proxy-Objekt für unser Server-RPC Interface und
		// rufen auf diesem die RPC-Methode setText() auf. Als Parameter
		// übergeben wir den aktuellen Inhalt des Textfeldes.
		getRpcProxy(ResettableTextAreaServerRpc.class).setText(getWidget().getText());
	}
}
