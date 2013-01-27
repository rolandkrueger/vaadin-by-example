package de.oio.vaadin.components;

import com.vaadin.data.util.converter.Converter.ConversionException;
import com.vaadin.ui.AbstractField;

import de.oio.vaadin.client.connectors.ResettableTextAreaState;
import de.oio.vaadin.client.rpc.ResettableTextAreaServerRpc;

public class ResettableTextArea extends AbstractField<String> {

	public ResettableTextArea() {
		registerRpc(new ResettableTextAreaServerRpc() {
			@Override
			public void setText(String text) {
				setValue(text);
			}
		});
	}

	@Override
	protected ResettableTextAreaState getState() {
		return (ResettableTextAreaState) super.getState();
	}

	public void setTextAreaUID(String uid) {
		getState().textAreaUID = uid;
	}

	public String getTextAreaUID() {
		return getState().textAreaUID;
	}

	@Override
	public void setValue(String newFieldValue) throws ReadOnlyException, ConversionException {
		super.setValue(newFieldValue);
		getState().text = newFieldValue;
	}

	@Override
	public Class<? extends String> getType() {
		return String.class;
	}
}
