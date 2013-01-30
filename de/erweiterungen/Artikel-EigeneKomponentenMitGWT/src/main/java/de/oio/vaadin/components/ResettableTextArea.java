package de.oio.vaadin.components;

import com.vaadin.data.Property;
import com.vaadin.data.util.converter.Converter.ConversionException;
import com.vaadin.shared.communication.SharedState;
import com.vaadin.ui.AbstractField;
import com.vaadin.ui.Field;

import de.oio.vaadin.client.connectors.ResettableTextAreaState;
import de.oio.vaadin.client.rpc.ResettableTextAreaServerRpc;

/**
 * Serverseitiger Teil unserer Komponente. Diese Klasse kann wie alle anderen
 * Vaadin UI-Komponenten auch in einer Vaadin-Anwendung verwendet werden.
 */
public class ResettableTextArea extends AbstractField<String> {

	public ResettableTextArea() {
		// Registrierung der Implementierung von unserem ServerRpc-Interface.
		// Die Implementierung muss als anonyme Klasse angegeben werden.
		registerRpc(new ResettableTextAreaServerRpc() {
			@Override
			public void setText(String text) {
				setValue(text);
			}
		});
	}

	/**
	 * Bekanntmachen unserer eigenen {@link SharedState}-Klasse. Es genügt, wenn
	 * wir dafür die Methode {@link #getState()} überschreiben und den
	 * Rückggabetyp auf unsere State-Klasse einschränken.
	 */
	@Override
	protected ResettableTextAreaState getState() {
		return (ResettableTextAreaState) super.getState();
	}

	/**
	 * Setzen der ID für das Textfeld. Der Zugriff auf das State-Objekt muss
	 * immer über die Methode {@link #getState()} erfolgen.
	 */
	public void setTextAreaUID(String uid) {
		getState().textAreaUID = uid;
	}

	public String getTextAreaUID() {
		return getState().textAreaUID;
	}

	/**
	 * Setzt den Wert dieser {@link Field}-Komponente und damit den Inhalt der
	 * {@link Property}.
	 */
	@Override
	public void setValue(String newFieldValue) throws ReadOnlyException, ConversionException {
		super.setValue(newFieldValue);

		// Der geänderte Wert muss auch dem clientseitigen Widget mitgeteilt
		// werden.
		getState().text = newFieldValue;
	}

	/**
	 * Muss für {@link AbstractField} implementiert werden. Wir geben damit
	 * einfach den Typ des von dieser Feldkomponente verwalteten Wertes zurück,
	 * in unserem Fall String.
	 */
	@Override
	public Class<? extends String> getType() {
		return String.class;
	}
}
