package de.oio.vaadin.client.connectors;

import com.vaadin.shared.AbstractFieldState;
import com.vaadin.shared.annotations.DelegateToWidget;

public class ResettableTextAreaState extends AbstractFieldState {

	@DelegateToWidget
	public String	textAreaUID;

	public String	text;
}
