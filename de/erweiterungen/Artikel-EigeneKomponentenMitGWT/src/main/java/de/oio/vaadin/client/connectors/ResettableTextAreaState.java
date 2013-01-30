package de.oio.vaadin.client.connectors;

import com.vaadin.shared.AbstractFieldState;
import com.vaadin.shared.annotations.DelegateToWidget;

import de.oio.vaadin.client.widgets.VResettableTextArea;

/**
 * Zustandsobjekt für unsere Komponente. Wir erben die Standardzustandsdaten von
 * {@link AbstractFieldState} und fügen diesen zwei eigene Zustandswerte hinzu.
 */
public class ResettableTextAreaState extends AbstractFieldState {

	/**
	 * ID des Textfeldes, die als Schlüssel in den Local Storage des Browsers
	 * dient. Durch die Annotation {@link DelegateToWidget} wird dafür gesorgt,
	 * dass das Framework geänderte Werte für die ID automatisch an die Methode
	 * {@link VResettableTextArea#setTextAreaUID(String)} durchschleift.
	 */
	@DelegateToWidget
	public String textAreaUID;

	/**
	 * Der Inhalt des Textfeldes.
	 */
	public String text;
}
