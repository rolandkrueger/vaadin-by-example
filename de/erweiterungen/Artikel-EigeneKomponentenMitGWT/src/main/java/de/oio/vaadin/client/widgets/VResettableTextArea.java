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

public class VResettableTextArea extends Composite implements Field {
	public static final String CLASSNAME = "v-resettabletextarea";

	private VerticalPanel panel;
	private ResetButton resetButton;
	private TextArea textArea;
	private Storage localStorage;
	private String textAreaUID = "";

	public VResettableTextArea() {
		super();

		resetButton = new ResetButton("Reset");
		addResetButtonClickHandler();

		panel = new VerticalPanel();
		textArea = new TextArea();
		panel.add(textArea);
		panel.add(resetButton);
		initWidget(panel);
		setStyleName(CLASSNAME);

		localStorage = Storage.getLocalStorageIfSupported();
		startSnapshotTimer();
		getCachedValueFromLocalStorage();

		VConsole.log("--- Local storage supported: " + Storage.isLocalStorageSupported());
	}

	private void addResetButtonClickHandler() {
		resetButton.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				setText("");
				if (localStorage != null) {
					localStorage.removeItem(textAreaUID);
				}
			}
		});
	}

	private void startSnapshotTimer() {
		if (localStorage != null) {
			Timer timer = new Timer() {
				public void run() {
					VConsole.log("--- Storing value to local storage for text area id: " + textAreaUID + " = "
							+ textArea.getText());
					saveContentToLocalStorage();
				}
			};
			timer.scheduleRepeating(5000);
		}
	}

	private void getCachedValueFromLocalStorage() {
		Scheduler.get().scheduleFinally(new ScheduledCommand() {
			public void execute() {
				if (localStorage != null) {
					setText(localStorage.getItem(textAreaUID));
				}
			}
		});
	}

	public void setTextAreaUID(String textAreaUID) {

		if (!this.textAreaUID.equals(textAreaUID)) {
			// Falls die ID geändert wurde, muss der unter der alten ID
			// gespeicherte Wert gelöscht werden.
			localStorage.removeItem(this.textAreaUID);
		}

		this.textAreaUID = textAreaUID == null ? "" : textAreaUID;
	}

	public TextArea getTextArea() {
		return textArea;
	}

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
