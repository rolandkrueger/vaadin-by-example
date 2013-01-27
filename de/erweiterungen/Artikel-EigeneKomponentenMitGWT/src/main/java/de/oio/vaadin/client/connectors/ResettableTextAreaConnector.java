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

@Connect(ResettableTextArea.class)
public class ResettableTextAreaConnector extends AbstractComponentConnector implements BlurHandler {

	public ResettableTextAreaConnector() {
		getWidget().getTextArea().addBlurHandler(this);
		getWidget().getResetButton().addClickHandler(new ClickHandler() {
			@Override
			public void onClick(ClickEvent event) {
				sendCurrentText();
			}
		});
	}

	@Override
	public VResettableTextArea getWidget() {
		return (VResettableTextArea) super.getWidget();
	}

	@Override
	protected Widget createWidget() {
		return GWT.create(VResettableTextArea.class);
	}

	@Override
	public ResettableTextAreaState getState() {
		return (ResettableTextAreaState) super.getState();
	}

	@Override
	public void onStateChanged(StateChangeEvent stateChangeEvent) {
		super.onStateChanged(stateChangeEvent);
		getWidget().setText(getState().text);
	}

	@Override
	public void onBlur(BlurEvent blurEvent) {
		sendCurrentText();
		getWidget().saveContentToLocalStorage();
	}

	private void sendCurrentText() {
		getRpcProxy(ResettableTextAreaServerRpc.class).setText(getWidget().getText());
	}
}
