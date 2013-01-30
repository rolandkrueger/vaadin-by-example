package de.oio.vaadin.client.widgets;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.storage.client.Storage;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.vaadin.client.VConsole;
import com.vaadin.client.ui.Field;

import de.oio.vaadin.client.connectors.ResettableTextAreaConnector;
import de.oio.vaadin.client.connectors.ResettableTextAreaState;

/**
 * Clientseitiges Widget für das zurücksetzbare Textfeld. Die Klasse erbt von
 * der GWT-Klasse {@link Composite}, da wir zwei UI Elemente (TextArea und
 * ResetButton) auf das Widget legen wollen.
 */
public class VResettableTextArea extends Composite implements Field {

	// eigener CSS-Klassenname für unser Widget
	public static final String CLASSNAME = "v-resettabletextarea";

	/**
	 * Das Panel, das die beiden Widgets TextArea und ResetButton aufnimmt.
	 */
	private VerticalPanel panel;
	private ResetButton resetButton;
	private TextArea textArea;

	/**
	 * Referenz auf die GWT-Hilfsklasse, mit der wir auf den Local Storage des
	 * Browsers zugreifen können.
	 */
	private Storage localStorage;

	/**
	 * Eindeutige ID für das Textfeld, mit der wir den zugehörigen Wert aus dem
	 * Local Storage lesen und schreiben können.
	 */
	private String textAreaUID = "";

	public VResettableTextArea() {
		super();

		resetButton = new ResetButton("Reset");
		// ClickHandler für den ResetButton definieren
		addResetButtonClickHandler();

		panel = new VerticalPanel();
		textArea = new TextArea();
		panel.add(textArea);
		panel.add(resetButton);

		// Festlegen des Composite-Inhalts. Muss im Konstruktor eines Composites
		// genau einmal aufgerufen werden.
		initWidget(panel);
		// Setzen unseres CSS-Klassennamens
		setStyleName(CLASSNAME);

		// Wenn der Browsers, in dem unser Widget dargestellt wird, den Local
		// Storage unterstützt, lassen wir uns eine Referenz auf die
		// Storage-Klasse geben.
		localStorage = Storage.getLocalStorageIfSupported();

		// Starten des Timers, der in regelmäßigen Abständen den aktuellen
		// Inhalt des Textfeldes im Local Storage sichert.
		startSnapshotTimer();

		// Falls für dieses Textfeld schon ein Eintrag im Local Storage
		// existiert, soll dieser geholt und in das Textfeld gesetzt werden. Das
		// ist dann der Fall, wenn der Inhalt des Textfeldes nicht gesichert
		// wurde, bevor der Browser geschlossen wurde.
		getCachedValueFromLocalStorage();

		// Wir geben noch auf die Debug-Konsole aus, ob der aktuelle Browser den
		// Local Storage unterstützt.
		VConsole.log("--- Local storage supported: " + Storage.isLocalStorageSupported());
	}

	/**
	 * Registriert einen GWT {@link ClickHandler} an unserem ResetButton.
	 */
	private void addResetButtonClickHandler() {
		resetButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				// Inhalt des Textfeldes leeren ...
				setText("");

				// ... und den dazugehörigen Eintrag im Local Storage löschen,
				// falls der Local Storage vom aktuellen Browser unterstützt
				// wird.
				if (localStorage != null) {
					localStorage.removeItem(textAreaUID);
				}
			}
		});
	}

	/**
	 * Konfiguriert und startet den Timer, der in regelmäßigen Abständen den
	 * aktuellen Inhalt des Textfeldes im Local Storage sichert.
	 */
	private void startSnapshotTimer() {
		// Timer nur starten, wenn der Local Storage unterstützt wird
		if (localStorage != null) {
			Timer timer = new Timer() {
				public void run() {
					VConsole.log("--- Storing value to local storage for text area id: " + textAreaUID + " = "
							+ textArea.getText());
					saveContentToLocalStorage();
				}
			};
			// der Timer soll jede 5 Sekunden aufgerufen werden
			timer.scheduleRepeating(5000);
		}
	}

	/**
	 * Holt den aktuellen Eintrag für die textAreaUID dieses Textfeldes aus dem
	 * Local Storage.
	 */
	private void getCachedValueFromLocalStorage() {
		// Das Auslesen des Wertes aus dem Local Storage soll nicht sofort bei
		// Aufruf dieser Methode geschehen (also innerhalb des Konstruktors)
		// sondern erst wenn die Browser Event Loop zurückgekehrt ist. Zu diesem
		// Zeitpunkt ist der ResettableTextArea ihre UID schon bekannt. Vorher
		// ist die textAreaUID noch null.
		Scheduler.get().scheduleDeferred(new ScheduledCommand() {
			public void execute() {
				if (localStorage != null) {
					// Text auslesen und dem Textfeld übergeben
					setText(localStorage.getItem(textAreaUID));
				}
			}
		});
	}

	/**
	 * Setzt die ID für dieses Textfeld. Diese ID wird als Schlüssel in den
	 * Local Storage des Browsers verwendet. Der Wert wird von der
	 * Serverkomponente gesetzt und über das {@link ResettableTextAreaState}
	 * Objekt an das Client Widget übertragen. Diese Methode wird dann von dem
	 * {@link ResettableTextAreaConnector} aufgerufen.
	 * 
	 * @param newTextAreaUID
	 *            die eindeutige ID für das Textfeld
	 */
	public void setTextAreaUID(String newTextAreaUID) {
		if (!this.textAreaUID.equals(newTextAreaUID)) {
			// Falls die ID geändert wurde, muss der unter der alten ID
			// gespeicherte Wert gelöscht werden.
			localStorage.removeItem(this.textAreaUID);
		}

		this.textAreaUID = newTextAreaUID == null ? "" : newTextAreaUID;
	}

	public TextArea getTextArea() {
		return textArea;
	}

	/**
	 * Speichert den aktuellen Inhalt des Textfeldes im Local Storage des
	 * Browsers. Als Schlüssel in den Browserspeicher wird die textAreaUID
	 * verwendet.
	 */
	public void saveContentToLocalStorage() {
		localStorage.setItem(textAreaUID, textArea.getText());
	}

	public void setText(String text) {
		textArea.setText(text);
	}

	public String getText() {
		return textArea.getText();
	}

	public ResetButton getResetButton() {
		return resetButton;
	}

	public void setWidth(String width) {
		textArea.setWidth(width);
	}

	public void setHeight(String height) {
		textArea.setHeight(height);
	}
}
